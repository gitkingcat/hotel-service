package com.ofg.microservice.hotel_service.repository.entity;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ReservationBuilder {

    private String roomName;

    private LocalDate startDate;

    private LocalDate endDate;

    public ReservationBuilder room(Room roomName) {
        this.roomName = roomName.getRoomName();
        return this;
    }

    public ReservationBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ReservationBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Reservation build() {
        Reservation reservation = new Reservation();
        reservation.setRoomName(roomName);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return reservation;
    }

}
