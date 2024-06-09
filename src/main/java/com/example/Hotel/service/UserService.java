package com.example.Hotel.service;

import com.example.Hotel.dto.*;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto save(UserRequestDto userRequestDto);
    PageResponseDto<UserResponseDto> findAll(Pageable pageable);
    UserResponseDto findById(Long id);

    void deleteById(Long id);
    UserResponseDto update(UserRequestDto userRequestDto, Long id);
}
