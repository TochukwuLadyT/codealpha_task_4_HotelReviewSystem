package com.ladyt.hotelReviewsystem.controller;

import com.ladyt.hotelReviewsystem.model.HotelReview;
import com.ladyt.hotelReviewsystem.model.HotelRoom;
import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import com.ladyt.hotelReviewsystem.response.HotelReviewResponse;
import com.ladyt.hotelReviewsystem.response.HotelRoomBookingResponse;
import com.ladyt.hotelReviewsystem.response.HotelRoomResponse;
import com.ladyt.hotelReviewsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/hotelReview")
public class ReviewController {

    private final ReviewService reviewService;

        @GetMapping("/all-reviews")
        public ResponseEntity<List<HotelReviewResponse>> getAllReviews() {
        List<HotelReview> reviews = reviewService.getAllReviews();
        List<HotelReviewResponse> reviewResponses = new ArrayList<>();
        for (HotelReview review : reviews) {
            HotelReviewResponse reviewResponse = getReviewResponse(review);
            reviewResponses.add(reviewResponse);
        }
        return ResponseEntity.ok(reviewResponses);
        }

        private HotelReviewResponse getReviewResponse(HotelReview review) {
            return new HotelReviewResponse(
                    review.getId(), review.getRating(), review.getComment());
        }



    @PostMapping("/add-review")
    public ResponseEntity<HotelReviewResponse> addReview(
            @RequestParam("rating") String rating,
            @RequestParam("comment") String comment){
        HotelReview savedReview = reviewService.addReview(rating, comment);
        HotelReviewResponse response = new HotelReviewResponse(savedReview.getId(), savedReview.getRating(),savedReview.getComment());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long id){
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
