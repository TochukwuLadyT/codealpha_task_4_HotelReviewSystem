package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.exception.InvalidBookingRequestException;
import com.ladyt.hotelReviewsystem.exception.ResourceNotFoundException;
import com.ladyt.hotelReviewsystem.model.HotelRoom;
import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import com.ladyt.hotelReviewsystem.repository.HotelRoomBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelBookingService implements IHotelBookingService{
    private final HotelRoomBookingRepository bookingRepository;
    private final IHotelRoomService hotelRoomService;
    public List<HotelRoomBooking> getAllHotelBookingsByRoomId(Long id) {
        return bookingRepository.findByRoomId(id);
    }

    @Override
    public void cancelBooking(Long id) {

        bookingRepository.deleteById(id);
    }

    @Override
    public String saveBooking(Long id, HotelRoomBooking bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check in date must not be after check out date");
        }
            HotelRoom hotelRoom = hotelRoomService.getRoomById(id).get();
            List<HotelRoomBooking> existingBookings = hotelRoom.getBookings();
            boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);

            if(roomIsAvailable ){
                hotelRoom.addBooking(bookingRequest);
                bookingRepository.save(bookingRequest);
            }else{
                throw new InvalidBookingRequestException("This room is not available for the selected date.");
            }
            return bookingRequest.getBookingConfirmationCode();
        }


    private boolean roomIsAvailable(HotelRoomBooking bookingRequest, List<HotelRoomBooking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );

    }

    @Override
    public HotelRoomBooking findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()->
                new ResourceNotFoundException("No booking found with the booking code: " +confirmationCode));
    }

    @Override
    public List<HotelRoomBooking> getAllBookings() {

        return bookingRepository.findAll();
    }
}
