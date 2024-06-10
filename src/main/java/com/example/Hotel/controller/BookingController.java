package com.example.Hotel.controller;

import com.example.Hotel.dto.*;
import com.example.Hotel.service.impl.BookingServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingService;
    @GetMapping
    public ResponseEntity<PageResponseDto<BookingResponseDto>> getAllHotels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.findAll(pageRequest));
    }
    @PostMapping
    public ResponseEntity<BookingResponseDto> createHotel(@RequestBody @Valid BookingRequestDto bookingRequestDto){
        return ResponseEntity.ok(bookingService.save(bookingRequestDto));
    }
}
