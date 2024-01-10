package com.ladyt.hotelReviewsystem.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
public class HotelRoomResponse {
    private Long id;
    private String location;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    private String photo;

    private List<HotelRoomBookingResponse> bookings;

    public HotelRoomResponse(Long id, String location, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.location = location;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }


    public HotelRoomResponse(Long id, String location, String roomType, BigDecimal roomPrice, boolean isBooked,
                             byte[] photoBytes){

        this.id = id;
        this.location = location;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes): null;

    }
}
