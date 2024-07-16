package com.ofg.microservice.hotel_service.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

import com.ofg.microservice.hotel_service.exceptions.HotelServiceException;
import com.ofg.microservice.hotel_service.exceptions.IncorrectDatesException;
import com.ofg.microservice.hotel_service.exceptions.MalformedDatesException;
import com.ofg.microservice.hotel_service.models.ReservationDTO;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class DataValidator {

    public void validateDates(ReservationDTO reservationDTO) throws HotelServiceException {
        validateDates(reservationDTO.getStartDate(), reservationDTO.getEndDate());
    }

    public void validateDates(String startDate, String endDate) throws HotelServiceException {
        LocalDate startLocalDate;
        LocalDate endLocalDate;
        try {
            startLocalDate = LocalDate.parse(startDate);
            endLocalDate = LocalDate.parse(endDate);
        } catch (DateTimeParseException parseException) {
            throw new MalformedDatesException();
        }
        if (!endLocalDate.isAfter(startLocalDate) || startLocalDate.isEqual(endLocalDate)) {
            throw new IncorrectDatesException();
        }
    }

}
