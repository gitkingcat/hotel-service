package com.ofg.microservice.hotel_service.exceptions;

public class ReservationNotPossibleException extends HotelServiceException {

    public ReservationNotPossibleException() {
        super("Reservation not possible for this dates.");
    }
}
