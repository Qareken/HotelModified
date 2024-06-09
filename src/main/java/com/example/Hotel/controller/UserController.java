package com.example.Hotel.controller;

import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.UserRequestDto;
import com.example.Hotel.dto.UserResponseDto;
import com.example.Hotel.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @GetMapping
    public ResponseEntity<PageResponseDto<UserResponseDto>> getAllHotels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAll(pageRequest));
    }
    @PostMapping
    public ResponseEntity<UserResponseDto> createHotel(@RequestBody @Valid UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.save(userRequestDto));
    }
    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }
    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/by-id/{id}")
    public ResponseEntity<UserResponseDto> updateById(@RequestBody @Valid UserRequestDto userRequestDto, @PathVariable Long id){
        return ResponseEntity.ok(userService.update(userRequestDto, id));
    }
}
