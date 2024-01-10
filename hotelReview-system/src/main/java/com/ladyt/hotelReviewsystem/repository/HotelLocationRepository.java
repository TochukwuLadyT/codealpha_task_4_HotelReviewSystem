package com.ladyt.hotelReviewsystem.repository;

import com.ladyt.hotelReviewsystem.model.HotelLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelLocationRepository extends JpaRepository<HotelLocation, Long> {
    @Query("SELECT DISTINCT r.location FROM HotelLocation r")
    List<String> findDistinctLocations();
}
