package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.model.HotelLocation;
import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IHotelLocationService {
    HotelLocation addNewHotelLocation(MultipartFile photo, String location, String description) throws IOException, SQLException;

    List<String> getAllLocations();

    List<HotelRoomBooking> getAllBookingsByLocationId(Long id);

}
