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
public class CreateReservationRequestDto {
    
    @NotBlank(message = "Имя клиента не может быть пустым")
    @Size(max = 255, message = "Имя клиента не должно превышать 255 символов")
    private String customerName;
    
    @Email(message = "Некорректный формат email")
    private String customerEmail;
    
    @NotBlank(message = "Телефон клиента не может быть пустым")
    @Size(max = 20, message = "Телефон не должен превышать 20 символов")
    private String customerPhone;
    
    @NotNull(message = "Время бронирования не может быть пустым")
    @Future(message = "Время бронирования должно быть в будущем")
    private LocalDateTime reservationTime;
    
    @NotNull(message = "Время начала не может быть пустым")
    private LocalDateTime startTime;
    
    @NotNull(message = "Время окончания не может быть пустым")
    private LocalDateTime endTime;
    
    @Min(value = 1, message = "Минимум 1 гость")
    @Max(value = 10, message = "Максимум 10 гостей")
    private Integer guestCount;
    
    @NotNull(message = "ID столика не может быть пустым")
    private Long diningTableId;
    
    @Size(max = 500, message = "Особые пожелания не должны превышать 500 символов")
    private String specialRequests;
}
