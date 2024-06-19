package com.example.Hotel.dto.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HotelSearch {
    private Long id;
    private String name;

    private String title;

    private String address;

    private String cityName;

    private double distance;

    private Integer rating;

    private Integer ratingCount;

}
