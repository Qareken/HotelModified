package com.example.Hotel.service;

import com.example.Hotel.dto.PageRequestDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.filter.HotelSearch;
import com.example.Hotel.dto.hotelDto.HotelRate;
import com.example.Hotel.dto.hotelDto.HotelResponseDto;
import org.springframework.data.domain.Pageable;

public interface HotelService {
    HotelResponseDto changeRate(HotelRate hotelRate);
    PageResponseDto<HotelResponseDto> search(HotelSearch hotelSearch, PageRequestDto pageRequestDto);
}
