package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.exception.InternalServerException;
import com.ladyt.hotelReviewsystem.exception.ResourceNotFoundException;
import com.ladyt.hotelReviewsystem.model.HotelRoom;
import com.ladyt.hotelReviewsystem.repository.HotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HotelRoomService implements IHotelRoomService {
    private final HotelRoomRepository hotelRoomRepository;

    @Override
    public HotelRoom addNewHotelRoom(MultipartFile file, String location, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setLocation(location);
        hotelRoom.setRoomType(roomType);
        hotelRoom.setRoomPrice(roomPrice);

        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            hotelRoom.setPhoto(photoBlob);
        }
        return hotelRoomRepository.save(hotelRoom);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return hotelRoomRepository.findDistinctRoomTypes();

    }

    @Override
    public List<HotelRoom> getAllHotelRooms() {
        return hotelRoomRepository.findAll();
    }

    @Override
    public byte[] getHotelRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<HotelRoom> theHotelRoom = hotelRoomRepository.findById(roomId);
        if (theHotelRoom.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, no room available for the location, please other locations");
        }
        Blob photoBlob = theHotelRoom.get().getPhoto();
        if (photoBlob != null) {
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteRoom(Long id) {
        Optional<HotelRoom> theRoom = hotelRoomRepository.findById(id);
        if (theRoom.isPresent()) {
            hotelRoomRepository.deleteById(id);
        }

    }

    @Override
    public HotelRoom updateRoom(Long id, String location, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        HotelRoom room = hotelRoomRepository.findById(id).get();
        if (roomType != null) room.setLocation(location);
        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new InternalServerException("Fail updating room");
            }
        }
        return hotelRoomRepository.save(room);
    }

    @Override
    public Optional<HotelRoom> getRoomById(Long id) {
        return Optional.of(hotelRoomRepository.findById(id).get());
    }

    @Override
    public List<HotelRoom> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String location, String roomType) {
          return hotelRoomRepository.findAvailableRoomsByDatesAndType(checkInDate,checkOutDate,location,roomType);
    }



}


