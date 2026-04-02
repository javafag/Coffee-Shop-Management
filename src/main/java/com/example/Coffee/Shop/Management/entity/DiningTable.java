package com.example.Coffee.Shop.Management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tables")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "table_number", unique = true)
    private String tableNumber;

    private Integer capacity;

    private Boolean isAvailable; // есть ли кто-то сейчас за ним

    private Boolean isReserved; // есть ли резервация

    private Boolean isActive; // нужен ли ремонт ему и тд, доступен ли он в целом.

    @OneToMany(mappedBy = "diningTable")  // ← "многие" Reservation ищут поле "diningTable"
    private List<Reservation> reservations;
}
