package com.example.Hotel.controller;

import com.example.Hotel.dto.*;
import com.example.Hotel.service.impl.BookingServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<PageResponseDto<BookingResponseDto>> getAllBooking(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.findAll(pageRequest));
    }
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody @Valid BookingRequestDto bookingRequestDto){
        return ResponseEntity.ok(bookingService.save(bookingRequestDto));
    }
}
