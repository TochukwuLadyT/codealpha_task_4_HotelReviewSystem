package com.ladyt.hotelReviewsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;


@Entity
@Getter
@Setter

public class HotelLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String description;
    @Lob
    private Blob photo;

}
