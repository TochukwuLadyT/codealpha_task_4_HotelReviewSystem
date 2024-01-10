package com.ladyt.hotelReviewsystem.controller;


import com.ladyt.hotelReviewsystem.exception.InvalidBookingRequestException;
import com.ladyt.hotelReviewsystem.exception.ResourceNotFoundException;
import com.ladyt.hotelReviewsystem.model.HotelRoom;
import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import com.ladyt.hotelReviewsystem.response.HotelRoomBookingResponse;
import com.ladyt.hotelReviewsystem.response.HotelRoomResponse;
import com.ladyt.hotelReviewsystem.service.IHotelBookingService;
import com.ladyt.hotelReviewsystem.service.IHotelRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/bookings")
public class HotelRoomBookingController {
    private final IHotelBookingService bookingService;
    private final IHotelRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<HotelRoomBookingResponse>> getAllBookings(){
        List<HotelRoomBooking> bookings = bookingService.getAllBookings();
            List<HotelRoomBookingResponse> bookingResponses =  new ArrayList<>();
            for (HotelRoomBooking booking: bookings){
                HotelRoomBookingResponse bookingResponse = getBookingResponse(booking);
                bookingResponses.add(bookingResponse);
            }
            return ResponseEntity.ok(bookingResponses);
    }

    private HotelRoomBookingResponse getBookingResponse(HotelRoomBooking booking) {
        HotelRoom theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        HotelRoomResponse hotelRoom = new HotelRoomResponse(theRoom.getId(),
                theRoom.getLocation(), theRoom.getRoomType(), theRoom.getRoomPrice());
        return new HotelRoomBookingResponse(
                booking.getId(),booking.getCheckOutDate(),
                booking.getCheckInDate(), booking.getLocation(),booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(), hotelRoom);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            HotelRoomBooking  booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            HotelRoomBookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/hotelRoom/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable("roomId") Long id,
                                         @RequestBody HotelRoomBooking bookingRequest){
        try{
            String confirmationCode = bookingService.saveBooking(id, bookingRequest);
            return ResponseEntity.ok("Room is booked successfully, " +
                    "Your booking confirmation code is :" +confirmationCode);
        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable("bookingId") Long id){
        bookingService.cancelBooking(id);
    }

}
