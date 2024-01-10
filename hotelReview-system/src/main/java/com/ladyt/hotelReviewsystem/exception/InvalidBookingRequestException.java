package com.ladyt.hotelReviewsystem.exception;

public class InvalidBookingRequestException extends RuntimeException{
    public  InvalidBookingRequestException(String message){
        super(message);
    }
}
