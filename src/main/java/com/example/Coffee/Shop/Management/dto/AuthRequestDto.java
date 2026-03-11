package com.example.Coffee.Shop.Management.dto;

import com.example.Coffee.Shop.Management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    private String password;

    private String username;

    private Role role;
}
