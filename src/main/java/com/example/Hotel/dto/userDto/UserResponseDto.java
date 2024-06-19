package com.example.Hotel.dto.userDto;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
}
