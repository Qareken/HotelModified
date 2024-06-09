package com.example.Hotel.service;

import com.example.Hotel.dto.*;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingResponseDto save(BookingRequestDto bookingRequestDto);
    PageResponseDto<BookingResponseDto> findAll(Pageable pageable);
}
