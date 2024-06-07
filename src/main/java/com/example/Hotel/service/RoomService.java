package com.example.Hotel.service;

import com.example.Hotel.dto.*;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    RoomResponseDto save(RoomRequestDto roomRequestDto);
    PageResponseDto<RoomResponseDto> findAll(Pageable pageable);
    RoomResponseDto findById(Long id);

    void deleteById(Long id);
    RoomResponseDto update(RoomRequestDto roomRequestDto, Long id);

}
