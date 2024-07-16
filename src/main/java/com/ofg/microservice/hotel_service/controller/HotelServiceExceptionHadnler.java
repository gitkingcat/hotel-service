package com.ofg.microservice.hotel_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ofg.microservice.hotel_service.exceptions.HotelServiceException;

@ControllerAdvice
public class HotelServiceExceptionHadnler {

    @ExceptionHandler(HotelServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleException(HotelServiceException hotelServiceException) {
        return "An error occurred: " + hotelServiceException.getMessage();
    }
}
