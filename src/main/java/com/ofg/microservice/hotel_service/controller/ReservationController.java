package com.ofg.microservice.hotel_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ofg.microservice.hotel_service.exceptions.HotelServiceException;
import com.ofg.microservice.hotel_service.models.ReservationDTO;
import com.ofg.microservice.hotel_service.service.RoomService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@EnableWebMvc
public class ReservationController {

    private RoomService roomService;

    @GetMapping("/availability")
    public ResponseEntity<String> checkAvailability(@RequestParam String startDate, @RequestParam String endDate) throws HotelServiceException {
        return ResponseEntity.ok(roomService.checkAvailability(startDate, endDate) ? "Room is available." : "");
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveRoom(@RequestBody ReservationDTO reservationDTO) throws HotelServiceException {
        return ResponseEntity.ok("Reservation successful. Reservation ID: " + roomService.reserveRoom(reservationDTO));
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable String reservationId) throws HotelServiceException {
        roomService.cancelReservation(reservationId);
        return ResponseEntity.ok("Reservation cancelled.");
    }

}
