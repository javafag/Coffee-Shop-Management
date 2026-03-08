package com.example.Coffee.Shop.Management.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CsfOrderRequestDto {

    @NotBlank(message = "Name mandatory")
    private String drinkName;

    @NotBlank(message = "Name mandatory")
    private String waiterName;

    @NotBlank(message = "Status mandatory")
    private String status;

    @NotNull(message = "Price mandatory")
    @Min(value = 0, message = "Price can't be less than 0")
    private java.math.BigDecimal price;

    @NotBlank(message = "Name mandatory")
    private String customerName;


}
