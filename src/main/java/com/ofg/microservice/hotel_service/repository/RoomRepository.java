package com.ofg.microservice.hotel_service.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ofg.microservice.hotel_service.repository.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT room FROM Room room "
        + "WHERE room.roomName NOT IN (  SELECT reservation.roomName FROM Reservation reservation   WHERE reservation.startDate <= :endDate  AND reservation.endDate >= "
        + ":startDate) ORDER BY room"
        + ".roomNumber ASC LIMIT 1")
    Optional<Room> findFirstAvailableRoom(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    @Query("SELECT CASE WHEN COUNT(room) > 0 THEN true ELSE false END " +
        "FROM Room room " +
        "WHERE NOT EXISTS " +
        "(SELECT 1 FROM Reservation reservation " +
        "WHERE reservation.roomName = room.roomName " +
        "AND (:startDate BETWEEN reservation.startDate AND reservation.endDate " +
        "OR :endDate BETWEEN reservation.startDate AND reservation.endDate)) ")
    boolean isRoomAvailable(@Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate);

}
