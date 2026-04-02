package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.Reservation;
import com.example.Coffee.Shop.Management.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {



    List<Reservation> findByDiningTableId(Long tableId);

    List<Reservation> findByStatus(ReservationStatus status);

    List<Reservation> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByDiningTableIdAndStartTimeBetween(Long tableId, LocalDateTime start, LocalDateTime end);

    List<Reservation> findByDiningTableIdAndReservationTimeBetween(Long tableId, LocalDateTime start, LocalDateTime end);

}
