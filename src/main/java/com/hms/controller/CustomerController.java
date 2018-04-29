package com.hms.controller;

import com.hms.helpers.Constant;
import com.hms.model.User;
import com.hms.model.UserProfile;
import com.hms.service.UserProfileService;
import com.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class CustomerController {

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final MessageSource messageSource;
    private final AuthenticationTrustResolver authenticationTrustResolver;

    @Autowired
    public CustomerController(UserService userService, UserProfileService userProfileService, MessageSource messageSource, AuthenticationTrustResolver authenticationTrustResolver) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.messageSource = messageSource;
        this.authenticationTrustResolver = authenticationTrustResolver;
    }

    /**
     * Register Page
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            User user = new User();
            model.addAttribute("user", user);
            model.addAttribute("edit", false);
            model.addAttribute("loggedinuser", getPrincipal());
            return "register";
        } else {
            return "redirect:/";
        }
    }

    /**
     * Validates & Register New Customer
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result,
                           HttpServletRequest request, ModelMap model) {

        if (result.hasErrors()) {
            return "register";
        }

        if (!userService.isUserUsernameUnique(user.getId(), user.getUsername())) {
            FieldError usernameError = new FieldError("user", "username", messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(usernameError);
            return "register";
        } else if (!userService.isUserEmailUnique(user.getId(), user.getEmail())) {
            FieldError emailError = new FieldError("user", "email", messageSource.getMessage("non.unique.email", new String[]{user.getEmail()}, Locale.getDefault()));
            result.addError(emailError);
            return "register";
        }

        try {
            com.hms.model.UserProfile role = userProfileService.findById(Constant.USER_ROLE.VERIFIED);
            Set<com.hms.model.UserProfile> userProfile = new HashSet<>();
            userProfile.add(role);
            user.setUserProfiles(userProfile);
            userService.saveUser(user);

            model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
            model.addAttribute("loggedinuser", getPrincipal());

            //Requesting LOGIN
            request.login(user.getEmail(), user.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping(value = "/user/profile-{email}/resend")
    public String resendConfirmationMail(@PathVariable("email") String username, ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            return "redirect:/login";
        }

        User user = getCurrentUser();
        if (user.getEmail().equals(username)) {
            user.setToken(UUID.randomUUID().toString());
            userService.updateUser(user);
        }
        return "redirect:/";
    }

    /**
     * Validates the user
     *
     * @param email customer to validate
     * @param token sent in user Email
     * @link sent in email confirmation email
     */
    @RequestMapping(value = "/user/profile-{email}/{token}/confirm")
    public String confirmUser(@PathVariable("email") String email, @PathVariable("token") String token,
                              RedirectAttributes redirectAttributes) {
        if (isCurrentAuthenticationAnonymous()) {
            return "redirect:/login";
        }

        User user = getCurrentUser();
        if (user.getUsername().equals(email)) {
            if (user.getToken().equals(token)) {
                user.setToken(null);
                com.hms.model.UserProfile role = userProfileService.findById(Constant.USER_ROLE.VERIFIED);
                Set<com.hms.model.UserProfile> userProfile = new HashSet<>();
                userProfile.add(role);
                user.setUserProfiles(userProfile);
                userService.updateUser(user);
                updateCurrentUser(user);
            } else {
                redirectAttributes.addFlashAttribute("success", "Confirmation expired. Kindly resend confirmation email");
            }
        } else {
            redirectAttributes.addFlashAttribute("success", "Kindly login with same account to confirm.");
        }
        return "redirect:/user/profile";
    }


    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getCurrentUserName(principal);
    }

    static String getCurrentUserName(Object principal) {
        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else if (principal instanceof User) {
            userName = ((User) principal).getEmail();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    private User getCurrentUser() {
        return userService.findByEmail(getPrincipal());
    }

    /**
     * This method returns true if users is already authenticated [logged-in]
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    private void updateCurrentUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, getGrantedAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (UserProfile userProfile : user.getUserProfiles())
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
        return authorities;
    }

}