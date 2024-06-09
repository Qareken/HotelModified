package com.example.Hotel.dto;

import com.example.Hotel.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
}
