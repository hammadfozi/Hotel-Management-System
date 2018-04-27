package com.hms.controller;

import com.hms.helpers.Constant;
import com.hms.model.Booking;
import com.hms.model.Room;
import com.hms.model.RoomType;
import com.hms.model.User;
import com.hms.service.BookingService;
import com.hms.service.RoomService;
import com.hms.service.RoomTypeService;
import com.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.hms.helpers.Constant.ROOM_STATUS.VERIFIED;

@Controller
@SessionAttributes("roles")
public class HotelController {

    private final UserService userService;
    private final RoomService roomService;
    private final BookingService bookingService;
    private final RoomTypeService roomTypeService;
    private final MessageSource messageSource;
    private final AuthenticationTrustResolver authenticationTrustResolver;

    @Autowired
    public HotelController(UserService userService, RoomService roomService, BookingService bookingService, RoomTypeService roomTypeService, MessageSource messageSource, AuthenticationTrustResolver authenticationTrustResolver) {
        this.userService = userService;
        this.roomService = roomService;
        this.bookingService = bookingService;
        this.roomTypeService = roomTypeService;
        this.messageSource = messageSource;
        this.authenticationTrustResolver = authenticationTrustResolver;
    }

    /**
     * Default date format to be used in app
     * binding to website data handlers
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    /**
     * Returns all rooms to be displayed on page
     *
     * @link /rooms
     */
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public String rooms(ModelMap model) {
        List<Room> rooms = roomService.findByStatus(VERIFIED);
        model.addAttribute("rooms", rooms);
        model.addAttribute("search", false);
        return "rooms";
    }

    @RequestMapping(value = "/rooms/search", method = RequestMethod.POST)
    public String searchRooms(@RequestParam("searchtext") String text, ModelMap model) {
        List<Room> rooms;

        // Modifying request to include search results
        if (text.toLowerCase().contains("fam".toLowerCase())
                || text.toLowerCase().contains(Constant.ROOM_TYPE.FAMILY.toLowerCase()))
            rooms = roomService.findByTypeId(Constant.ROOM_TYPE_VALUE.FAMILY);
        else if (text.toLowerCase().contains("del".toLowerCase())
                || text.toLowerCase().contains(Constant.ROOM_TYPE.DELUXE.toLowerCase()))
            rooms = roomService.findByTypeId(Constant.ROOM_TYPE_VALUE.DELUXE);
        else if (text.toLowerCase().contains("exe".toLowerCase())
                || text.toLowerCase().contains(Constant.ROOM_TYPE.EXECUTIVE.toLowerCase()))
            rooms = roomService.findByTypeId(Constant.ROOM_TYPE_VALUE.EXECUTIVE);
        else if (text.toLowerCase().contains("free".toLowerCase())
                || text.toLowerCase().contains("avail".toLowerCase())
                || text.toLowerCase().contains("free rooms".toLowerCase())
                || text.toLowerCase().contains("available".toLowerCase()))
            rooms = roomService.findFreeRooms();
        else if (text.toLowerCase().contains("all".toLowerCase())
                || text.toLowerCase().contains("all rooms".toLowerCase()))
            rooms = roomService.findByStatus(VERIFIED);
        else rooms = roomService.searchByName(text);
        model.addAttribute("rooms", rooms);
        model.addAttribute("searchtext", text);
        model.addAttribute("search", true);
        return "rooms";
    }

    /**
     * This method handles the Booking
     */
    @RequestMapping(value = "/booking", method = RequestMethod.GET)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public String book(ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            return "redirect:/login";
        } else {
            Booking booking = new Booking();
            model.addAttribute("booking", booking);
            addBookingAttributes(model);
            return "booking";
        }
    }

    /**
     * @param id room id to be booked
     * @Temporal(DATE) important for using defined date databinding
     */
    @RequestMapping(value = "/booking-{id}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public String bookSpecificRoom(@PathVariable Integer id, ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            return "redirect:/login";
        } else {
            Room room = roomService.findById(id);

            if (room.getBooking() != null || roomService.findById(id) == null)
                return "redirect:/rooms";

            Booking booking = new Booking();
            booking.setRoom(room);
            model.addAttribute("booking", booking);
            addBookingAttributes(model);
            return "booking";
        }
    }

    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public String bookRoom(@Valid @ModelAttribute Booking booking, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            addBookingAttributes(model);
            return "booking";
        }

        if (booking.getUser() == null) booking.setUser(getCurrentUser());

        // awaiting confirmation from manager
        booking.setStatus(Constant.BOOKING_STATUS.PENDING);
        bookingService.saveBooking(booking);

        model.addAttribute("success", "Booking ID " + booking.getId() + " created successfully");
        model.addAttribute("bookingsuccess", booking.getId());
        return "success";
    }

    /**
     * Attributes in new booking
     */
    private void addBookingAttributes(ModelMap model) {
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("rooms", roomService.findFreeRooms());
        model.addAttribute("edit", false);
    }

    /**
     * This method will provide RoomType list to views
     */
    @ModelAttribute("types")
    public List<RoomType> initializeTypes() {
        return roomTypeService.findAll();
    }

    /**
     * Attributes in edit booking
     */
    private void addBookingAttributes(ModelMap model, Booking booking) {
        model.addAttribute("booking", booking);
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("rooms", roomService.findFreeRooms());
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("edit", true);
    }

    /**
     * ROLES : Manager
     * <p>
     * Edit the booking
     *
     * @param id booking to edit
     * @link /manage/bookings/edit-{id}
     */
    @RequestMapping(value = "/manage/bookings/edit-{id}", method = RequestMethod.GET)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public String booking(ModelMap model, @PathVariable Integer id, RedirectAttributes redirectAttrs) {
        if (isCurrentAuthenticationAnonymous()) {
            return "redirect:/login";
        } else {

            if (bookingService.findById(id) == null) {
                redirectAttrs.addFlashAttribute("success", "There is no booking with Id " + id + " in system.");
                return "redirect:/manage";
            }

            Booking booking = bookingService.findById(id);
            model.addAttribute("booking", booking);
            model.addAttribute("user", getCurrentUser());
            model.addAttribute("users", userService.findAllUsers());
            model.addAttribute("rooms", roomService.findAllRooms());
            model.addAttribute("loggedinuser", getPrincipal());
            model.addAttribute("edit", true);
            return "editBooking";
        }
    }

    /**
     * ROLES : Manager
     *
     * @param id booking to edit
     * @link /manage/bookings/edit-{id}
     */
    @RequestMapping(value = "/manage/bookings/edit-{id}", method = RequestMethod.POST)
    public String updateBooking(@ModelAttribute Booking booking, BindingResult result,
                                ModelMap model, @PathVariable Integer id, RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            addBookingAttributes(model, booking);
            return "editBooking";
        }

        // Error handling
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        Date currentTime = null;
        try {
            currentTime = dateFormat.parse(dateFormat.format(cal.getTime()));
        } catch (ParseException p) {
            p.printStackTrace();
        }
        if (currentTime != null) {
            if (booking.getArrivalTime().before(currentTime)) {
                FieldError dateError = new FieldError("booking", "arrivalTime",
                        messageSource.getMessage("non.error.before_arrival", new Date[]{booking.getArrivalTime()}, Locale.getDefault()));
                result.addError(dateError);
                addBookingAttributes(model, booking);
                return "editBooking";
            } else if (booking.getDepartureTime().before(currentTime)) {
                FieldError dateError = new FieldError("booking", "departureTime",
                        messageSource.getMessage("non.error.before_departure", new Date[]{booking.getDepartureTime()}, Locale.getDefault()));
                result.addError(dateError);
                addBookingAttributes(model, booking);
                return "editBooking";
            }
        }

        // check date errors
        if (booking.getArrivalTime().after(booking.getDepartureTime())) {
            FieldError dateError = new FieldError("booking", "arrivalTime",
                    messageSource.getMessage("non.error.arrival_after", new Date[]{booking.getArrivalTime()}, Locale.getDefault()));
            FieldError dateError_ = new FieldError("booking", "departureTime",
                    messageSource.getMessage("non.error.departure_before", new Date[]{booking.getDepartureTime()}, Locale.getDefault()));
            result.addError(dateError);
            result.addError(dateError_);
            addBookingAttributes(model, booking);
            return "editBooking";
        }

        bookingService.updateBooking(booking);
        redirectAttrs.addFlashAttribute("success", "Booking No " + id + " was updated successfully");
        return "redirect:/manage";
    }

    @RequestMapping(value = "/manage/bookings/delete-{id}")
    public String deleteBooking(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        if (bookingService.findById(id) == null) {
            redirectAttrs.addFlashAttribute("success", "Booking No " + id + " does not exist in database.");
            return "redirect:/manage";
        }

        if (bookingService.findById(id).getStatus().equals(Constant.BOOKING_STATUS.COMPLETED)) {
            redirectAttrs.addFlashAttribute("success", "Booking No " + id + " has been completed, it can't be removed from database.");
            return "redirect:/manage";
        }

        bookingService.deleteBookingById(id);
        redirectAttrs.addFlashAttribute("success", "Booking No " + id + " was deleted successfully");
        return "redirect:/manage";
    }

    /**
     * ROLES: MANAGER
     */
    @RequestMapping(value = "/manage/rooms/edit-{id}", method = RequestMethod.POST)
    public String updateRoomPrice(@PathVariable Integer id, @RequestParam("price") Integer roomPrice,
                                  RedirectAttributes redirectAttributes) {
        Room room = roomService.findById(id);
        room.setPrice(roomPrice);
        room.setStatus(VERIFIED);
        roomService.updateRoom(room);
        redirectAttributes.addFlashAttribute("success", "Room " + id + " has been updated and verified.");
        return "redirect:/manage";
    }

    /**
     * ROLES: MANAGER
     * <p>
     * Confirm customer booking
     */
    @RequestMapping(value = "/manage/bookings/confirm-{id}")
    public String confirmBooking(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        Booking booking = bookingService.findById(id);

        if (booking == null) {
            redirectAttrs.addFlashAttribute("success", "Booking No " + id + " does not exist in database.");
            return "redirect:/manage";
        }

        if (booking.getStatus().equals(Constant.BOOKING_STATUS.COMPLETED)) {
            redirectAttrs.addFlashAttribute("success", "Booking No " + booking + " has been completed, it can't be set to CONFIRMED.");
            return "redirect:/manage";
        }
        booking.setStatus(Constant.BOOKING_STATUS.CONFIRMED);
        bookingService.updateBooking(booking);
        redirectAttrs.addFlashAttribute("success", "Booking No " + id + " was set to confirmed");
        return "redirect:/manage";
    }

    /**
     * ROLES: MANAGER
     * <p>
     * Finish / Complete customer booking
     */
    @RequestMapping(value = "/manage/bookings/complete-{id}")
    public String completeBooking(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        Booking booking = bookingService.findById(id);

        if (booking == null) {
            redirectAttrs.addFlashAttribute("success", "Booking No " + id + " does not exist in database.");
            return "redirect:/manage";
        }

        booking.setRoomBooked(booking.getRoom().getName());
        booking.setStatus(Constant.BOOKING_STATUS.COMPLETED);
        booking.setRoom(null);
        bookingService.updateBooking(booking);
        redirectAttrs.addFlashAttribute("success", "Booking No " + id + " was set to completed");
        return "redirect:/manage";
    }

    @RequestMapping(value = "/manage/rooms/verify-{id}")
    public String verifyRoom(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Room room = roomService.findById(id);
        room.setStatus(VERIFIED);
        roomService.updateRoom(room);
        redirectAttributes.addFlashAttribute("success", "Room " + id + " has been verified.");
        return "redirect:/manage";
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

}
