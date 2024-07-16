package com.ofg.microservice.hotel_service.exceptions;

public class ReservationDoesNotExists extends HotelServiceException {
    public ReservationDoesNotExists() {
        super("Reservation does not exist");
    }
}
