package com.example.Coffee.Shop.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsfErrorResponseDto {
    private Integer status;
    private String code;
    private String msg;
    private String path;
    private Map<String, String> fieldErrors;
}



