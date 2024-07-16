package com.ofg.microservice.hotel_service.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ofg.microservice.hotel_service.exceptions.HotelServiceException;
import com.ofg.microservice.hotel_service.repository.RoomRepository;
import com.ofg.microservice.hotel_service.repository.entity.Reservation;
import com.ofg.microservice.hotel_service.exceptions.ReservationDoesNotExists;
import com.ofg.microservice.hotel_service.exceptions.RoomNotAvailableException;
import com.ofg.microservice.hotel_service.exceptions.ReservationNotPossibleException;
import com.ofg.microservice.hotel_service.repository.ReservationRepository;
import com.ofg.microservice.hotel_service.models.ReservationDTO;
import com.ofg.microservice.hotel_service.repository.entity.ReservationBuilder;
import com.ofg.microservice.hotel_service.repository.entity.Room;
import com.ofg.microservice.hotel_service.validator.DataValidator;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService {

    private ReservationRepository reservationRepository;

    private RoomRepository roomRepository;

    private DataValidator dataValidator;

    private ReservationBuilder reservationBuilder;

    public Boolean checkAvailability(String startDate, String endDate) throws HotelServiceException {
        dataValidator.validateDates(startDate, endDate);
        return Optional.of(roomRepository.isRoomAvailable(LocalDate.parse(startDate), LocalDate.parse(endDate)))
            .filter(Boolean::booleanValue)
            .orElseThrow(RoomNotAvailableException::new);
    }

    @Transactional
    public Long reserveRoom(ReservationDTO reservationDTO) throws HotelServiceException {
        dataValidator.validateDates(reservationDTO);
        Reservation reservation = reservationBuilder
            .room(findAvailableRoom(reservationDTO.getStartDate(), reservationDTO.getEndDate()))
            .startDate(LocalDate.parse(reservationDTO.getStartDate()))
            .endDate(LocalDate.parse(reservationDTO.getEndDate()))
            .build();
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    @Transactional
    public void cancelReservation(String reservationId) throws ReservationDoesNotExists {
        reservationRepository.findById(Long.parseLong(reservationId)).orElseThrow(ReservationDoesNotExists::new);
        reservationRepository.deleteById(Long.valueOf(reservationId));
    }

    private Room findAvailableRoom(String startDate, String endDate) throws ReservationNotPossibleException {
        return roomRepository.findFirstAvailableRoom(LocalDate.parse(startDate), LocalDate.parse(endDate)).orElseThrow(ReservationNotPossibleException::new);
    }
}
