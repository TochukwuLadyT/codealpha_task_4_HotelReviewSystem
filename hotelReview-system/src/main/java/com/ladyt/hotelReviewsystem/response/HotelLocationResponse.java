package com.ladyt.hotelReviewsystem.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelLocationResponse {
    private Long id;
    private String location;
    private String description;
    private String photo;

    public HotelLocationResponse(Long id, String location, String description) {
        this.id = id;
        this.location = location;
        this.description = description;
    }

    public HotelLocationResponse(Long id, String location, String description, byte[] photoBytes) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes): null;
    }
}
