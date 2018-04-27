package com.hms.service;

import com.hms.model.Booking;
import com.hms.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service("bookingService")
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository dao;

    public Booking findById(int id) {
        Optional<Booking> tmp = dao.findById(id);
        return tmp.orElse(null);
    }

    public Booking findByRoomId(final int id) {
        return dao.findByRoomId(id);
    }

    public List<Booking> findByUserId(final int id) {
        return dao.findByUserId(id);
    }

    public List<Booking> findByTypeId(final int id) {
        return dao.findByRoomTypeId(id);
    }

    public void saveBooking(@NotNull Booking booking) {
        dao.save(booking);
    }

    public void updateBooking(@NotNull Booking booking) {
        Optional<Booking> entity = dao.findById(booking.getId());
        if (entity.isPresent()) {
            booking.setId(entity.get().getId());
            dao.save(booking);
        }
    }

    public void deleteBookingById(final int id) {
        dao.deleteById(id);
    }

    public void deleteBookingByRoomId(final int id) {
        dao.deleteByRoomId(id);
    }

    public void deleteBookingByUserId(final int id) {
        dao.deleteByUserId(id);
    }

    public List<Booking> findAllBookings() {
        return dao.findAll();
    }

    public boolean isBookingUnique(final int id, final int roomId) {
        Booking booking = findByRoomId(roomId);
        return ((booking == null) || (booking.getId() == id));
    }

    public List<Booking> findPendingBookings() {
        return dao.findByStatus("PENDING");
    }

    public List<Booking> findConfirmedBookings() {
        return dao.findByStatus("CONFIRMED");
    }

    public List<Booking> findCompletedBookings() {
        return dao.findByStatus("COMPLETED");
    }
}
