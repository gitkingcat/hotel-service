package com.ofg.microservice.hotel_service.exceptions;

public class IncorrectDatesException extends HotelServiceException {
    public IncorrectDatesException() {
        super("Dates are incorrect. End date should be after start date.");
    }
}
