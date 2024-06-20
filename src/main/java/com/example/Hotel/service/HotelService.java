package com.example.Hotel.service;

import com.example.Hotel.dto.filter.CommonSearch;
import com.example.Hotel.dto.filter.PageRequestDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.filter.HotelSearch;
import com.example.Hotel.dto.hotelDto.HotelRate;
import com.example.Hotel.dto.hotelDto.HotelResponseDto;

public interface HotelService {
    HotelResponseDto changeRate(HotelRate hotelRate);
    PageResponseDto<HotelResponseDto> search(CommonSearch<HotelSearch> hotelSearch);
}
