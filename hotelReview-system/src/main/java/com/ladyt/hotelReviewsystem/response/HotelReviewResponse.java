package com.ladyt.hotelReviewsystem.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelReviewResponse {
    private Long id;
    private int rating;
    private String comment;

    public HotelReviewResponse(Long id, int rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
    }

    public HotelReviewResponse(Long id, int rating, String comment, HotelReviewResponse hotelReview) {

    }
}
