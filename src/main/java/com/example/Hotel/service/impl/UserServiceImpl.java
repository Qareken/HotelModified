package com.example.Hotel.service.impl;

import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.UserRequestDto;
import com.example.Hotel.dto.UserResponseDto;
import com.example.Hotel.entity.Role;
import com.example.Hotel.entity.Users;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.exception.EntityNotFoundException;
import com.example.Hotel.mapper.PageMapper;
import com.example.Hotel.mapper.UserMapper;
import com.example.Hotel.repository.RoleRepository;
import com.example.Hotel.repository.UserRepository;
import com.example.Hotel.service.UserService;
import com.example.Hotel.service.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final RoleRepository roleRepository;


    @Override
    public UserResponseDto save(UserRequestDto userRequestDto) {
        var user = userMapper.toEntity(userRequestDto);
        existedByNameOrEmail(user);
        Set<Role> roles = user.getRoles();
        Set<Role> persistedRoles = new HashSet<>();
        for (Role role : roles) {
            Role existingRole = roleRepository.findByAuthority(role.getAuthority());
            persistedRoles.add(Objects.requireNonNullElse(existingRole, role));
        }
        user.setRoles(persistedRoles);

        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public PageResponseDto<UserResponseDto> findAll(Pageable pageable) {
        return pageMapper.toPageResponseDto(userRepository.findAll(pageable).map(userMapper::toUserResponseDto));
    }

    protected Users findUsersById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with this {} id not found!!", id)));
    }

    @Override
    public UserResponseDto findById(Long id) {
        return userMapper.toUserResponseDto(findUsersById(id));
    }

    @Override
    public Users findByName(String name) {
        return userRepository.findUsersByName(name).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with this {} name not found!!", name)));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private void existedByNameOrEmail(Users users) {
        if (userRepository.existsByNameOrEmail(users.getName(), users.getEmail())) {
            throw new BadRequestException("Name or email already exist please use another");
        }
    }

    @Override
    public UserResponseDto update(UserRequestDto userRequestDto, Long id) {
        var existedUser = findUsersById(id);
        var user = userMapper.toEntity(userRequestDto);
        BeanUtils.copyNonNullProperties(user, existedUser);
        existedByNameOrEmail(existedUser);
        return userMapper.toUserResponseDto(userRepository.save(existedUser));
    }
}
