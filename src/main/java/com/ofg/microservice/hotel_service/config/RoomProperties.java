package com.ofg.microservice.hotel_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("hotel-room-reservation")
@Data
public class RoomProperties {

    int roomNumber;

}
