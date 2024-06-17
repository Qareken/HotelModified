package com.example.Hotel.service;

import com.example.Hotel.dto.HotelRate;
import com.example.Hotel.dto.HotelRequestDto;
import com.example.Hotel.dto.HotelResponseDto;
import com.example.Hotel.dto.PageResponseDto;
import org.springframework.data.domain.Pageable;

public interface HotelService {
    HotelResponseDto save(HotelRequestDto hotelRequestDto);
    PageResponseDto<HotelResponseDto> findAll(Pageable pageable);
    HotelResponseDto findById(Long id);

    void deleteById(Long id);
    HotelResponseDto update(HotelRequestDto hotelRequestDto, Long id);
    HotelResponseDto changeRate(HotelRate hotelRate);
}
