package com.ofg.microservice.hotel_service.exceptions;

public class RoomNotAvailableException extends HotelServiceException {
    public RoomNotAvailableException() {
        super("There are no rooms available.");
    }
}
