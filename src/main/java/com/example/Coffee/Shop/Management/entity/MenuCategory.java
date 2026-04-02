package com.example.Coffee.Shop.Management.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "menu_categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private Integer displayOrder;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;


}
