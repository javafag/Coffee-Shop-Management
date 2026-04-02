package com.example.Coffee.Shop.Management.dto;

import com.example.Coffee.Shop.Management.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReservationResponseDto {
    private ReservationStatus status;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDateTime reservationTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer guestCount;
    private Long diningTableId;
    private String specialRequests;
}
