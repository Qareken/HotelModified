package com.example.Hotel.service.impl;

import com.example.Hotel.dto.userDto.UserRequestDto;
import com.example.Hotel.dto.userDto.UserResponseDto;
import com.example.Hotel.entity.Role;
import com.example.Hotel.entity.Users;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.exception.EntityNotFoundException;
import com.example.Hotel.mapper.CommonMapper;
import com.example.Hotel.repository.CommonRepository;
import com.example.Hotel.repository.RoleRepository;
import com.example.Hotel.repository.UserRepository;
import com.example.Hotel.service.UserService;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Service
public class UserServiceImpl extends CommonServiceImpl<UserRequestDto , UserResponseDto, Users, Long> implements UserService  {
    public UserServiceImpl(CommonRepository<Users, Long> repository, CommonMapper<Users, UserRequestDto, UserResponseDto> mapper, RoleRepository roleRepository) {
        super(repository, mapper);
        this.roleRepository = roleRepository;
    }
    private final RoleRepository roleRepository;
    @Override
    public UserResponseDto save(UserRequestDto userRequestDto) {
        var user = mapper.toEntity(userRequestDto);
        existedByNameOrEmail(user);
        Set<Role> roles = user.getRoles();
        Set<Role> persistedRoles = new HashSet<>();
        for (Role role : roles) {
            Role existingRole = roleRepository.findByAuthority(role.getAuthority());
            persistedRoles.add(Objects.requireNonNullElse(existingRole, role));
        }
        user.setRoles(persistedRoles);
        return mapper.toResponseDto(repository.save(user));
    }
    @Override
    public Users findByName(String name) {
        return ((UserRepository)repository).findUsersByName(name).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with this {} name not found!!", name)));
    }
    private void existedByNameOrEmail(Users users) {
        if (((UserRepository) repository).existsByNameOrEmail(users.getName(), users.getEmail())) {
            throw new BadRequestException("Name or email already exist please use another");
        }
    }
}
