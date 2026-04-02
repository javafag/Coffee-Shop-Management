package com.example.Coffee.Shop.Management.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReservationRequestDto {
    
    @Size(max = 255, message = "Имя клиента не должно превышать 255 символов")
    private String customerName;
    
    @Email(message = "Некорректный формат email")
    private String customerEmail;
    
    @Size(max = 20, message = "Телефон не должен превышать 20 символов")
    private String customerPhone;
    
    private LocalDateTime reservationTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @Min(value = 1, message = "Минимум 1 гость")
    @Max(value = 10, message = "Максимум 10 гостей")
    private Integer guestCount;
    
    @Size(max = 500, message = "Особые пожелания не должны превышать 500 символов")
    private String specialRequests;
}
