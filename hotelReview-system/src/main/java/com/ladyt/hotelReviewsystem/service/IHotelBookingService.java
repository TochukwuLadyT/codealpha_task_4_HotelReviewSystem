package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;

import java.util.List;

public interface IHotelBookingService {
    void cancelBooking(Long id);

    String saveBooking(Long id, HotelRoomBooking bookingRequest);

    HotelRoomBooking findByBookingConfirmationCode(String confirmationCode);

    List<HotelRoomBooking> getAllBookings();
}
