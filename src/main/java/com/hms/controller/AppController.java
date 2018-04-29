package com.hms.controller;

import com.hms.helpers.Constant;
import com.hms.model.Feedback;
import com.hms.model.User;
import com.hms.model.UserProfile;
import com.hms.repository.FeedbackRepository;
import com.hms.service.BookingService;
import com.hms.service.RoomTypeService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("roles")
public class AppController {

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final FeedbackRepository feedbackRepository;
    private final RoomTypeService roomTypeService;
    private final BookingService bookingService;
    private final MessageSource messageSource;
    private final AuthenticationTrustResolver authenticationTrustResolver;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppController(UserService userService, UserProfileService userProfileService, RoomTypeService roomTypeService, BookingService bookingService, MessageSource messageSource, AuthenticationTrustResolver authenticationTrustResolver, PasswordEncoder passwordEncoder, FeedbackRepository feedbackRepository) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.roomTypeService = roomTypeService;
        this.bookingService = bookingService;
        this.messageSource = messageSource;
        this.authenticationTrustResolver = authenticationTrustResolver;
        this.passwordEncoder = passwordEncoder;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * @link HOME PAGE
     */
    @RequestMapping(value = {"/", "/home"})
    public String home(ModelMap model) {
        model.addAttribute("familybase", roomTypeService.findById(Constant.ROOM_TYPE_VALUE.FAMILY).getBasePrice());
        model.addAttribute("executivebase", roomTypeService.findById(Constant.ROOM_TYPE_VALUE.EXECUTIVE).getBasePrice());
        model.addAttribute("deluxebase", roomTypeService.findById(Constant.ROOM_TYPE_VALUE.DELUXE).getBasePrice());
        return "index";
    }

    /**
     * @param username - optional
     * @param email    - optional
     * @link /register/available?username=username&email=email
     */
    @RequestMapping(value = "/user/availability")
    public
    @ResponseBody
    String checkUserAvailability(@RequestParam(value = "username", defaultValue = "") String username,
                                 @RequestParam(value = "email", defaultValue = "") String email) {
        User user = null;
        if (username != null && !username.equals("")) user = userService.findByUsername(username);
        if (email != null && !email.equals("")) user = userService.findByEmail(email);
        if (user == null) return "Available";
        else return "Not Available";
    }

    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     *
     * @see com.hms.security.SecurityConfiguration for detailed login properties
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            User user = getCurrentUser();
            model.addAttribute("username", getPrincipal());
            model.addAttribute("user", user);
            model.addAttribute("totalbookings", user.getBookings().size());
            return "profile";
        }
    }

    /**
     * Update profile settings request handler
     */
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public String updateProfile(@Valid @ModelAttribute User user, BindingResult result,
                                ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {

            // take to login if not authenticated yet
            return "login";
        } else {

            // check if both {username & email} are unique
            if (!userService.isUserUsernameUnique(user.getId(), user.getUsername())) {
                FieldError usernameError = new FieldError("user", "username", messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
                result.addError(usernameError);
                return "profile";
            } else if (!userService.isUserEmailUnique(user.getId(), user.getEmail())) {
                FieldError emailError = new FieldError("user", "email", messageSource.getMessage("non.unique.email", new String[]{user.getEmail()}, Locale.getDefault()));
                result.addError(emailError);
                return "profile";
            }

            User n = getCurrentUser();
            n.setFirstName(user.getFirstName());
            n.setLastName(user.getLastName());
            n.setUsername(user.getUsername());
            n.setEmail(user.getEmail());

            userService.updateUser(n);
            updateCurrentUser(n);
            model.addAttribute("success", "Your profile was updated successfully");
            return "profile";
        }
    }

    @RequestMapping(value = "/user/profile-loggedin+edit", method = RequestMethod.POST)
    public String updatePassword(@RequestParam("newpassword") String password, RedirectAttributes redirectAttributes) {
        User n = getCurrentUser();
        n.setPassword(passwordEncoder.encode(password));
        userService.updateUser(n);
        redirectAttributes.addFlashAttribute("success", "Your password was changed successfully");
        return "redirect:/user/profile";
    }

    /**
     * Customer cancelling his booking profile
     */
    @RequestMapping(value = "/user/profile/cancel-{id}+confirmed")
    public String deleteBooking(@PathVariable int id, RedirectAttributes redirectAttributes) {

        if (bookingService.findById(id).getStatus().equals(Constant.BOOKING_STATUS.COMPLETED)) {
            redirectAttributes.addFlashAttribute("success", "Booking No " + id + " has been completed already, it can not be cancelled.");
            return "redirect:/user/profile";
        }

        Integer booking = id;
        bookingService.deleteBookingById(id);
        redirectAttributes.addFlashAttribute("success", "Your Booking (" + booking + ") was cancelled successfully");
        return "redirect:/user/profile";
    }

    @RequestMapping(value = "/user/profile/delete")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
        String username = getCurrentUser().getUsername();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        new SecurityContextLogoutHandler().logout(request, response, auth);
        SecurityContextHolder.getContext().setAuthentication(null);

        userService.deleteUserByUsername(username);
        return "redirect:/";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return CustomerController.getCurrentUserName(principal);
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

    /**
     * Updates the current user to new values
     *
     * @param user updated settings
     */
    private void updateCurrentUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, getGrantedAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserProfile userProfile : user.getUserProfiles())
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
        return authorities;
    }


    // ERRORS

    /**
     * This method handles Access-Denied redirect.
     * For unauthorized
     *
     * @link com.hms.security.SecurityConfiguration for configuring access to links
     */
    @RequestMapping(value = "/errors/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }

    @RequestMapping(value = {"/errors/404"})
    public String pageNotFound() {
        return "404";
    }

    /**
     * Generic Mapping
     */
    @RequestMapping(value = "/contact")
    public String contact(ModelMap model) {
        model.addAttribute("feedback", new Feedback());
        return "contact";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public String feedback(@ModelAttribute Feedback feedback) {
        feedbackRepository.save(feedback);
        return "redirect:/";
    }

    @RequestMapping(value = "/about")
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/privacy")
    public String policy() {
        return "policy";
    }
}