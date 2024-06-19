package com.example.Hotel.service;

import com.example.Hotel.dto.*;
import com.example.Hotel.entity.Users;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Users findByName(String name);
}
