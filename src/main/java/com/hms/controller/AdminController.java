package com.hms.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hms.helpers.Constant;
import com.hms.model.*;
import com.hms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("roles")
public class AdminController {

    private final UserService userService;
    private final RoomService roomService;
    private final RoomTypeService roomTypeService;
    private final RoomImageService roomImagesService;
    private final BookingService bookingService;
    private final UserProfileService userProfileService;
    private final MessageSource messageSource;

    @Autowired
    public AdminController(@Lazy UserService userService, RoomService roomService, RoomTypeService roomTypeService, RoomImageService roomImagesService, BookingService bookingService, UserProfileService userProfileService, MessageSource messageSource) {
        this.userService = userService;
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
        this.roomImagesService = roomImagesService;
        this.bookingService = bookingService;
        this.userProfileService = userProfileService;
        this.messageSource = messageSource;
    }

    /**
     * Set additional attributes required for all Admin pages
     *
     * @param model Response model for Admin page
     */
    private void setAttributes(ModelMap model) {
        List<User> customers = userService.findAllCustomers();
        List<User> admins = userService.findAllAdmins();
        List<User> managers = userService.findAllManagers();
        List<Room> rooms = roomService.findAllRooms();
        List<Booking> bookings = bookingService.findAllBookings();
        model.addAttribute("username", getPrincipal());
        model.addAttribute("customers", customers);
        model.addAttribute("admins", admins);
        model.addAttribute("managers", managers);
        model.addAttribute("rooms", rooms);
        model.addAttribute("bookings", bookings);
        model.addAttribute("totalcustomers", customers.size());
        model.addAttribute("totalmanagers", managers.size());
        model.addAttribute("totaladmins", admins.size());
        model.addAttribute("totalbookings", bookings.size());
        model.addAttribute("totalrooms", rooms.size());
        model.addAttribute("images", roomImagesService.findAll());
    }

    @RequestMapping(value = "/admin")
    public String adminHome(ModelMap model) {
        setAttributes(model);
        return "admin";
    }


    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = "/admin/new/user", method = RequestMethod.GET)
    public String newStaff(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "adminCreateUser";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = "/admin/new/user", method = RequestMethod.POST)
    public String saveStaff(@ModelAttribute User user, BindingResult result,
                            ModelMap model, RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            return "adminCreateUser";
        }

        if (!userService.isUserUsernameUnique(user.getId(), user.getUsername())) {
            FieldError usernameError = new FieldError("user", "username", messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(usernameError);
            return "adminCreateUser";
        } else if (!userService.isUserEmailUnique(user.getId(), user.getEmail())) {
            FieldError emailError = new FieldError("user", "email", messageSource.getMessage("non.unique.email", new String[]{user.getEmail()}, Locale.getDefault()));
            result.addError(emailError);
            return "adminCreateUser";
        }

        userService.saveUser(user);

        redirectAttrs.addFlashAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " was created successfully");
        return "redirect:/admin";
    }

    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = {"admin/user/edit-{username}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String username, ModelMap model, RedirectAttributes redirectAttrs) {

        User user = userService.findByUsername(username);
        if (user == null) {
            redirectAttrs.addFlashAttribute("success", "Request user: " + username + " does not exist in database.");
            return "redirect:/manage";
        }
        user.setPassword("");

        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "editUser";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     *
     * @link /admin/user/edit/delete-myname
     */
    @RequestMapping(value = {"admin/user/edit-{username}"}, method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user, BindingResult result,
                             @PathVariable String username, RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            return "editUser";
        }

        if (!userService.isUserUsernameUnique(user.getId(), user.getUsername())) {
            FieldError usernameError = new FieldError("user", "username", messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(usernameError);
            return "editUser";
        } else if (!userService.isUserEmailUnique(user.getId(), user.getEmail())) {
            FieldError emailError = new FieldError("user", "email", messageSource.getMessage("non.unique.email", new String[]{user.getEmail()}, Locale.getDefault()));
            result.addError(emailError);
            return "editUser";
        }
        User u = userService.findByUsername(username);
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setUserProfiles(user.getUserProfiles());
        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        userService.updateUser(u);
        redirectAttrs.addFlashAttribute("success", "User: " + username + " was updated successfully");
        return "redirect:/admin";
    }

    /**
     * This method will delete user by it's Username
     *
     * @link /admin/user/delete-myname
     */
    @RequestMapping(value = {"admin/user/delete-{username}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String username, RedirectAttributes redirectAttrs) {

        if (userService.findByUsername(username) == null) {
            redirectAttrs.addFlashAttribute("success", "Requested user: " + username + " does not exist in database.");
            return "redirect:/admin";
        }

        userService.deleteUserByUsername(username);
        redirectAttrs.addFlashAttribute("success", "User " + username + " was deleted successfully");
        return "redirect:/admin";
    }

    /**
     * This method will provide user roles to all views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }


    /**
     * This method will provide the medium to add a new room.
     */
    @RequestMapping(value = "/admin/new/room", method = RequestMethod.GET)
    public String newRoom(ModelMap model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("edit", false);
        return "adminAddRoom";
    }


    /**
     * This method will be called on form submission, handling POST request for
     * saving room in database.
     */
    @RequestMapping(value = "/admin/new/room", method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute Room room, BindingResult result, RedirectAttributes redirectAttrs,
                           MultipartHttpServletRequest request) throws IOException {

        if (result.hasErrors()) {
            return "adminAddRoom";
        }

        if (!roomService.isRoomNameUnique(room.getId(), room.getName())) {
            FieldError nameError = new FieldError("room", "name", messageSource.getMessage("non.unique.room", new String[]{room.getName()}, Locale.getDefault()));
            result.addError(nameError);
            return "adminAddRoom";
        } else if (room.getPrice() < room.getType().getBasePrice()) {
            FieldError priceError = new FieldError("room", "price", messageSource.getMessage("conflict.room_price", new String[]{room.getType().getType()}, Locale.getDefault()));
            result.addError(priceError);
            return "adminAddRoom";
        }

        room.setStatus(Constant.ROOM_STATUS.UNVERIFIED);
        roomService.saveRoom(room);
        saveImage(room, request.getFiles("pictures"));

        redirectAttrs.addFlashAttribute("success", "Room " + room.getName() + " was added successfully");
        return "redirect:/admin";
    }

    /**
     * @param name - name of room
     * @link /admin/new/room/check?name=roomName
     */
    @RequestMapping(value = "/admin/room/check")
    public
    @ResponseBody
    String checkRoomAvailability(@RequestParam("name") String name) {
        if (roomService.isRoomNameUnique(null, name)) return "Available";
        return "Not Available";
    }

    @RequestMapping(value = "/admin/room/edit-{id}", method = RequestMethod.GET)
    public String editRoom(@PathVariable Integer id, ModelMap model) {

        if (roomService.findById(id) == null)
            return "redirect:/admin";

        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        model.addAttribute("edit", true);
        return "editRoom";
    }

    /**
     * Request handler for saving the updated
     *
     * @link /admin/room/edit-xxxx
     */
    @RequestMapping(value = "/admin/room/edit-{id}", method = RequestMethod.POST)
    public String updateRoom(@ModelAttribute Room room, BindingResult result,
                             @PathVariable Integer id,
                             MultipartHttpServletRequest request, RedirectAttributes redirectAttrs) throws IOException {

        if (result.hasErrors()) {
            return "editRoom";
        }

        if (!roomService.isRoomNameUnique(room.getId(), room.getName())) {
            FieldError nameError = new FieldError("room", "name", messageSource.getMessage("non.unique.room", new String[]{room.getName()}, Locale.getDefault()));
            result.addError(nameError);
            return "editRoom";
        } else if (room.getPrice() < room.getType().getBasePrice()) {
            FieldError priceError = new FieldError("room", "price", messageSource.getMessage("conflict.room_price", new String[]{room.getType().getType()}, Locale.getDefault()));
            result.addError(priceError);
            return "editRoom";
        }

        Room r = roomService.findById(id);
        if (!(r.getPrice().equals(room.getPrice()))) {
            r.setStatus(Constant.ROOM_STATUS.UNVERIFIED);
            r.setPrice(room.getPrice());
        }
        r.setType(room.getType());
        r.setBath(room.getBath());
        r.setBooking(room.getBooking());
        r.setBed(room.getBed());
        r.setCapacity(room.getCapacity());
        r.setDescription(room.getDescription());
        r.setName(room.getName());

        // check image attachments
        List<MultipartFile> images = request.getFiles("pictures");
        if (images.size() != 0 && !(images.size() == 1 && images.get(0).getSize() == 0)) {
            saveImage(room, images);
            r.setImages(room.getImages());
        }
        roomService.updateRoom(r);

        redirectAttrs.addFlashAttribute("success", "Room " + room.getName() + " was updated successfully");
        return "redirect:/admin";
    }

    /**
     * Delete all images belonging to the room
     *
     * @param room images to be deleted from
     */
    private void deleteRoomImages(Room room) {
        List<String> publicIds = new ArrayList<>();
        for (RoomImages image : room.getImages()) {
            publicIds.add(image.getName());
        }

        final String CLOUDINARY_URL = "cloudinary://758212679231862:MhnrcmQObbrLDfGdQI4T1kVhj8M@hte2zx5qx";
        Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);
        try {
            cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        roomImagesService.deleteByRoomId(room.getId());
    }

    /**
     * Save room images in the database and local storage
     *
     * @param room   images to be saved to
     * @param images multipart
     */
    private void saveImage(final Room room, List<MultipartFile> images) throws IOException {
        final String CLOUDINARY_URL = "cloudinary://758212679231862:MhnrcmQObbrLDfGdQI4T1kVhj8M@hte2zx5qx";
        Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);

        for (int i = 0; i < images.size(); i++) {
            String roomName = room.getName();
            roomName = roomName.replaceAll("\\s", "");
            roomName = roomName + "_" + i;

            cloudinary.uploader().upload(images.get(i).getBytes(),
                    ObjectUtils.asMap("public_id", roomName));

            String extension = ".jpg";
            if (images.get(i).getContentType().toLowerCase().contains("png")) {
                extension = ".png";
            } else if (images.get(i).getContentType().toLowerCase().contains("tif")) {
                extension = ".tif";
            }

            RoomImages roomImage = new RoomImages();
            roomImage.setName(roomName);
            roomImage.setUrl(cloudinary.url().generate(roomName) + extension);
            roomImage.setRoom(room);
            roomImagesService.save(roomImage);
        }
    }

    @RequestMapping(value = "/admin/room/images/delete-{id}")
    public String deleteRoomImage(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        if (roomImagesService.findById(id) == null) {
            redirectAttrs.addFlashAttribute("success", "Room with Id " + id + "does not exit.");
            return "redirect:/admin";
        }

        List<String> publicIds = new ArrayList<>();
        publicIds.add(roomImagesService.findById(id).getName());

        final String CLOUDINARY_URL = "cloudinary://758212679231862:MhnrcmQObbrLDfGdQI4T1kVhj8M@hte2zx5qx";
        Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);
        try {
            cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        roomImagesService.deleteById(id);
        redirectAttrs.addFlashAttribute("success", "Image No " + id + " was removed successfully.");
        return "redirect:/admin";
    }

    /**
     * Deletes the room and redirects url with appropriate message
     *
     * @return redirect url
     * @link /admin/room/delete-xxxx
     */
    @RequestMapping(value = "/admin/room/delete-{id}", method = RequestMethod.GET)
    public String deleteRoom(@PathVariable Integer id, RedirectAttributes redirectAttrs) {

        if (roomService.findById(id) == null) {
            redirectAttrs.addFlashAttribute("success", "Room with Id " + id + "does not exit.");
            return "redirect:/admin";
        }

        roomImagesService.deleteByRoomId(id);
        roomService.deleteRoomById(id);
        redirectAttrs.addFlashAttribute("success", "Room No " + id + " was removed successfully.");
        return "redirect:/admin";
    }

    /**
     * @param model attributes required by manager dashboard
     * @link /manage
     */
    @RequestMapping(value = "/manage")
    public String managerHome(ModelMap model) {
        setAttributes(model);
        List<Booking> bookings = bookingService.findPendingBookings();
        List<Booking> bookings1 = bookingService.findConfirmedBookings();
        List<Booking> bookings2 = bookingService.findCompletedBookings();
        List<Room> freerooms = roomService.findFreeRooms();
        List<Room> unverifiedrooms = roomService.findByStatus(Constant.ROOM_STATUS.UNVERIFIED);

        model.addAttribute("freerooms", freerooms);
        model.addAttribute("unverifiedrooms", unverifiedrooms);
        model.addAttribute("totalfreerooms", freerooms.size());
        model.addAttribute("totalunverifiedrooms", unverifiedrooms.size());
        model.addAttribute("pendingbookings", bookings);
        model.addAttribute("confirmedbookings", bookings1);
        model.addAttribute("completedbookings", bookings2);
        model.addAttribute("totalpendingbookings", bookings.size());
        model.addAttribute("totalconfirmedbookings", bookings1.size());
        return "admin";
    }

    /**
     * This method will provide RoomType list to views
     */
    @ModelAttribute("types")
    public List<RoomType> initializeTypes() {
        return roomTypeService.findAll();
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return CustomerController.getCurrentUserName(principal);
    }
}