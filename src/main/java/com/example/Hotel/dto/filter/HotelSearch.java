package com.example.Hotel.dto.filter;

import lombok.Data;

@Data
public class HotelSearch {

    private String name;

    private String title;

    private String address;

    private String cityName;

    private double distance;

    private Integer rating;

    private Integer ratingCount;

}
