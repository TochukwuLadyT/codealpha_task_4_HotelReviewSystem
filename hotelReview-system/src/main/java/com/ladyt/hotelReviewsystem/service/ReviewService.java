package com.ladyt.hotelReviewsystem.service;

import com.ladyt.hotelReviewsystem.model.HotelReview;
import com.ladyt.hotelReviewsystem.model.HotelRoom;
import com.ladyt.hotelReviewsystem.repository.HotelReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService implements IReviewService{
    private final HotelReviewRepository hotelReviewRepository;

    @Override
    public HotelReview addReview(String rating, String comment) {
        HotelReview hotel = new HotelReview();
        hotel.setRating(Integer.parseInt(rating));
        hotel.setComment(comment);
        return hotelReviewRepository.save(hotel);
    }

    @Override
    public List<com.ladyt.hotelReviewsystem.model.HotelReview> getAllReviews() {
        return hotelReviewRepository.findAll();
    }

    @Override
    public Optional<HotelReview> getReviewById(Long id) {
        return Optional.of(hotelReviewRepository.findById(id).get());
    }

    @Override
    public void deleteReview(Long id) {
        Optional<HotelReview> theReview = hotelReviewRepository.findById(id);
        if (theReview.isPresent()) {
            hotelReviewRepository.deleteById(id);
        }
    }

}
