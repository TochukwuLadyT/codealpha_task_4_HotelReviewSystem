package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.model.HotelReview;

import java.util.List;
import java.util.Optional;

public interface IReviewService {

    HotelReview addReview(String rating, String comment);

    List<HotelReview> getAllReviews();

    Optional<HotelReview> getReviewById(Long id);

    void deleteReview(Long id);
}




