package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.model.HotelRoom;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHotelRoomService {
    HotelRoom addNewHotelRoom(MultipartFile photo, String location, String roomType, BigDecimal roomPrice) throws IOException, SQLException;

    List<String> getAllRoomTypes();

    List<HotelRoom> getAllHotelRooms();

    byte[] getHotelRoomPhotoByRoomId(Long id) throws SQLException;

    void deleteRoom(Long id);

    HotelRoom updateRoom(Long id, String location, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    Optional<HotelRoom> getRoomById(Long id);


    List<HotelRoom> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String location, String roomType);

//  List<HotelRoom> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
