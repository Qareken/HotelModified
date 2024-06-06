package com.example.Hotel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelRequestDto {
    @NotBlank(message = "name must not be an empty")
    private String name;
    @NotBlank(message = "title must not be an empty")
    private String title;
    @NotBlank(message = "address must not be an empty")
    private String address;
    @NotBlank(message = "cityName must not be an empty")
    private String cityName;
    @Min(value = 0, message = "Distance must be a non-negative number")
    private double distance;
}
