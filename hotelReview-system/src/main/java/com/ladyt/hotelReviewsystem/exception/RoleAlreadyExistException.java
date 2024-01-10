package com.ladyt.hotelReviewsystem.exception;

public class RoleAlreadyExistException extends RuntimeException{
    public RoleAlreadyExistException(String message){
        super(message);
    }
}
