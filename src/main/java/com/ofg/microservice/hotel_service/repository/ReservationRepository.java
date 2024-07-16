package com.ofg.microservice.hotel_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ofg.microservice.hotel_service.repository.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteById(Long id);

}
