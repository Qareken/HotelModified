package com.example.Hotel.service;

import com.example.Hotel.dto.hotelDto.HotelRate;
import com.example.Hotel.dto.hotelDto.HotelResponseDto;

public interface HotelService {
    HotelResponseDto changeRate(HotelRate hotelRate);
}
