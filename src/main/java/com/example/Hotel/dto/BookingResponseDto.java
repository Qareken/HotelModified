package com.example.Hotel.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingResponseDto {
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private UserResponseDto userResponseDto;
    private RoomResponseDto roomResponseDto;
}
