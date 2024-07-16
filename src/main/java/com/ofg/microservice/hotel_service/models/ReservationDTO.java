package com.ofg.microservice.hotel_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservationDTO {

    private String startDate;

    private String endDate;

}
