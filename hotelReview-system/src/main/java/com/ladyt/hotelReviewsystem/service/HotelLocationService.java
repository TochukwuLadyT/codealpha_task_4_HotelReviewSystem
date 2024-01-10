package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.exception.ResourceNotFoundException;
import com.ladyt.hotelReviewsystem.model.HotelLocation;
import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import com.ladyt.hotelReviewsystem.repository.HotelLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class HotelLocationService implements IHotelLocationService {
    private final HotelLocationRepository hotelLocationRepository;

    @Override
    public HotelLocation addNewHotelLocation(MultipartFile file, String location, String description) throws IOException, SQLException {
        HotelLocation hotel = new HotelLocation();
        hotel.setLocation(location);
        hotel.setDescription(description);
        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            hotel.setPhoto(photoBlob);
        }
        return hotelLocationRepository.save(hotel);
    }

    @Override
    public List<String> getAllLocations() {

        return hotelLocationRepository.findDistinctLocations();
    }

    @Override
    public List<HotelRoomBooking> getAllBookingsByLocationId(Long id) {
        return null;
    }

}
