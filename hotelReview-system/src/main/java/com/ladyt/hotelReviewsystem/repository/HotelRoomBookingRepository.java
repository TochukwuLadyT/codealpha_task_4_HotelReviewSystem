package com.ladyt.hotelReviewsystem.repository;

import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HotelRoomBookingRepository extends JpaRepository <HotelRoomBooking, Long>{
        List <HotelRoomBooking> findByRoomId(Long id);

        Optional<HotelRoomBooking> findByBookingConfirmationCode(String confirmationCode);
}
