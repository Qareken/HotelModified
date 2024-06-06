package com.example.Hotel.dto;

import lombok.Data;

@Data
public class HotelResponseDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private double distance;
    private int rating;
    private int numberOfRatings;
}
