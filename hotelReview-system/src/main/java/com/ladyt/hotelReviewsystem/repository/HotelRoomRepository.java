package com.ladyt.hotelReviewsystem.repository;

import com.ladyt.hotelReviewsystem.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {

    @Query("SELECT DISTINCT r.roomType FROM HotelRoom r")
    List<String> findDistinctRoomTypes();


    @Query(" SELECT r FROM HotelRoom r " +
            " WHERE r.roomType LIKE %:roomType% AND r.location LIKE %:location% "+
            " AND r.id NOT IN (" +
            " SELECT br.room.id FROM HotelRoomBooking br " +
            " WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
            ")")
    List<HotelRoom> findAvailableRoomsByDatesAndType(LocalDate checkInDate, LocalDate checkOutDate, String location, String roomType);

}

