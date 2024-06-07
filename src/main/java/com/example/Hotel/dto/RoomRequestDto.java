package com.example.Hotel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
@Data
public class RoomRequestDto {
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotNull(message = "Number of Room must not be null")
    private Integer numberOfRoom;

    @NotNull(message = "Price must not be null")
    private BigDecimal price;

    @NotNull(message = "Max Occupancy must not be null")
    @Min(value = 1, message = "Max Occupancy must be at least 1")
    private Integer maxOccupancy;

    private Set<LocalDateTime> unavailableDates;

    @NotNull(message = "Hotel ID must not be null")
    private Long hotelId;
}
