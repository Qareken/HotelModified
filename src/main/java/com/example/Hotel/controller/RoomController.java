package com.example.Hotel.controller;

import com.example.Hotel.dto.*;
import com.example.Hotel.service.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomServiceImpl roomService;
    @GetMapping
    public ResponseEntity<PageResponseDto<RoomResponseDto>> getAllHotels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok().body(roomService.findALL(pageRequest));
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponseDto> createHotel(@RequestBody @Validated RoomRequestDto roomRequestDto){
        return ResponseEntity.ok().body(roomService.save(roomRequestDto));
    }
    @GetMapping("/by-id/{id}")
    public ResponseEntity<RoomResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(roomService.findById(id));
    }
    @DeleteMapping("/by-id/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        roomService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/by-id/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponseDto> updateById(@RequestBody @Validated RoomRequestDto roomRequestDto,@PathVariable Long id){
        return ResponseEntity.ok().body(roomService.update(roomRequestDto, id));
    }
}
