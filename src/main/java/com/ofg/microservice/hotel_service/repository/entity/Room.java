package com.ofg.microservice.hotel_service.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "id", nullable = false)
    private int roomNumber;

    @Column(name = "room_name")
    private String roomName;
}