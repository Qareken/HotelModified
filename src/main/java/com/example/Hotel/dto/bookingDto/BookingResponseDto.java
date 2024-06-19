package com.example.Hotel.dto.bookingDto;

import com.example.Hotel.dto.userDto.UserResponseDto;
import com.example.Hotel.dto.roomDto.RoomResponseDto;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingResponseDto {
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private UserResponseDto userResponseDto;
    private RoomResponseDto roomResponseDto;
}
