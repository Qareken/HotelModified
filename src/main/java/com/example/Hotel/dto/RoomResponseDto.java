package com.example.Hotel.dto;

import com.example.Hotel.entity.UnAvailableDates;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class RoomResponseDto {
    private Long id;
    private String name;
    private Integer numberOfRoom;
    private BigDecimal price;
    private Integer maxOccupancy;
    private Set<UnAvailableDates> unavailableDates;
    private HotelResponseDto hotel;
}
