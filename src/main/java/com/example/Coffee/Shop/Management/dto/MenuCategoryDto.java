package com.example.Coffee.Shop.Management.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryDto {
    private Long id;

    @NotBlank(message = "Название категории не может быть пустым")
    @Size(min = 2, max = 50, message = "Название категории должно быть от 2 до 50 символов")
    private String name;

    private Integer displayOrder;
}