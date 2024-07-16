package com.ofg.microservice.hotel_service.exceptions;

public class MalformedDatesException extends HotelServiceException {
    public MalformedDatesException() {
        super("Dates are incorrect. Date format should be yyyy-MM-dd");
    }
}
