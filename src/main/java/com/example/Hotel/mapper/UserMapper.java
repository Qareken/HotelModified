package com.example.Hotel.mapper;


import com.example.Hotel.dto.userDto.UserRequestDto;
import com.example.Hotel.dto.userDto.UserResponseDto;
import com.example.Hotel.entity.Role;

import com.example.Hotel.entity.Users;
import com.example.Hotel.entity.enumurated.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends CommonMapper<Users, UserRequestDto, UserResponseDto> {
    default Set<Role> mapRolesFromNameToRole(Set<String> roles){
        return roles.stream().map(RoleType::fromLabel)
                .map(Role::from).collect(Collectors.toSet());

    }
    default Set<String> mapRolesFromRoleToName(Set<Role> roles){
        return roles.stream().map(Role::getAuthority).map(RoleType::getLabel).collect(Collectors.toSet());
    }
    @Mapping(target = "roles", expression = "java(mapRolesFromNameToRole(userRequestDto.getRoles()))")
    Users toEntity(UserRequestDto userRequestDto);
    @Mapping(target = "roles",expression = "java(mapRolesFromRoleToName(users.getRoles()))")
    UserRequestDto toDto(Users users);
    @Mapping(target = "roles",expression = "java(mapRolesFromRoleToName(users.getRoles()))")
    UserResponseDto toResponseDto(Users users);
}
