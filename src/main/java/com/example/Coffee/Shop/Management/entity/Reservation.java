package com.example.Coffee.Shop.Management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String customerName;


    @Email
    private String customerEmail;

    @NotBlank
    private String customerPhone;

    @NotNull
    @Future
    private LocalDateTime reservationTime;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @Min(1)
    private Integer guestCount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private String specialRequests;


    @ManyToOne
    @JoinColumn(name = "dining_table_id")  // ← foreign key в таблице reservations
    private DiningTable diningTable;
}
