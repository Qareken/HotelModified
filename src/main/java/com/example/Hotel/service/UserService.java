package com.example.Hotel.service;

import com.example.Hotel.dto.*;
import com.example.Hotel.entity.Users;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto save(UserRequestDto userRequestDto);
    PageResponseDto<UserResponseDto> findAll(Pageable pageable);
    UserResponseDto findById(Long id);
    Users findByName(String name);

    void deleteById(Long id);
    UserResponseDto update(UserRequestDto userRequestDto, Long id);
}
