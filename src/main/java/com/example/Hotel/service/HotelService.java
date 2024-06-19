package com.example.Hotel.service;

import com.example.Hotel.dto.HotelRate;
import com.example.Hotel.dto.HotelRequestDto;
import com.example.Hotel.dto.HotelResponseDto;
import com.example.Hotel.dto.PageResponseDto;
import org.springframework.data.domain.Pageable;

public interface HotelService {
    HotelResponseDto changeRate(HotelRate hotelRate);
}
