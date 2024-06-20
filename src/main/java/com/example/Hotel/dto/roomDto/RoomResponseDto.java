package com.example.Hotel.dto.roomDto;

import com.example.Hotel.dto.hotelDto.HotelResponseDto;
import com.example.Hotel.entity.UnAvailableDates;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class RoomResponseDto {
    private Long id;
    private String name;
    private String title;
    private Integer numberOfRoom;
    private BigDecimal price;
    private Integer maxOccupancy;
    private Set<UnAvailableDates> unavailableDates;
    private HotelResponseDto hotel;
}
