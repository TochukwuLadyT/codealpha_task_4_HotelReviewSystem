package com.ladyt.hotelReviewsystem.controller;

import com.ladyt.hotelReviewsystem.exception.PhotoRetrievalException;
import com.ladyt.hotelReviewsystem.model.HotelLocation;
import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import com.ladyt.hotelReviewsystem.response.HotelLocationResponse;
import com.ladyt.hotelReviewsystem.response.HotelRoomBookingResponse;
import com.ladyt.hotelReviewsystem.response.HotelRoomResponse;
import com.ladyt.hotelReviewsystem.service.IHotelLocationService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/locations")
public class HotelLocationController {
   private final IHotelLocationService hotelLocationService;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/new-location")
    public ResponseEntity<HotelLocationResponse> addHotelLocation(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("location") String location,
            @RequestParam("description") String description) throws SQLException, IOException {
        HotelLocation savedHotel = hotelLocationService.addNewHotelLocation(photo, location, description);
        HotelLocationResponse response = new HotelLocationResponse(savedHotel.getId(),
                savedHotel.getLocation(), savedHotel.getDescription());
        return ResponseEntity.ok(response);
    }

    private List<HotelRoomBooking> getAllBookingsByRoomId(Long id) {
        return hotelLocationService.getAllBookingsByLocationId(id);
    }


}
