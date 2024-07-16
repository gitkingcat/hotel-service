package com.ofg.microservice.hotel_service.config;

import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ofg.microservice.hotel_service.repository.RoomRepository;
import com.ofg.microservice.hotel_service.repository.entity.Room;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@AllArgsConstructor
public class RoomConfig {

    RoomRepository roomRepository;

    RoomProperties roomProperties;

    @Bean
    public CommandLineRunner initDatabase(RoomRepository roomRepository) {
        return args -> {
            IntStream.rangeClosed(1, roomProperties.getRoomNumber())
                .mapToObj(i -> {
                    Room room = new Room();
                    room.setRoomNumber(i);
                    room.setRoomName("Room " + i);
                    return room;
                })
                .forEach(roomRepository::save);
        };
    }
}
