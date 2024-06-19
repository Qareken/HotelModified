package com.example.Hotel.aop;

import com.example.Hotel.dto.userDto.UserRequestDto;
import com.example.Hotel.entity.enumurated.RoleType;
import com.example.Hotel.exception.ValidException;
import com.example.Hotel.security.AppUserDetails;
import com.example.Hotel.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class UserAop {
    private final UserServiceImpl userService;
    @Pointcut(value = "execution(* com.example.Hotel.controller.UserController.*ById(..))")
    public void checkForDeleteOrFind(){
    }
    @Before(value = "checkForDeleteOrFind()&& args(id, userDetails)", argNames ="id, userDetails" )
    public void deleteOrFindUser(Long id, UserDetails userDetails){
        var user = userService.findByName(userDetails.getUsername());
        if(userDetails instanceof AppUserDetails &&userDetails.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_USER.name())) ){
            if(!Objects.equals(user.getId(), id)){
                throw new ValidException("Access denied");
            }
        }
    }
    @Before(value = "checkForDeleteOrFind()&& args(userRequestDto,id, userDetails)", argNames ="userRequestDto,id, userDetails" )
    public void updateUser(UserRequestDto userRequestDto,Long id, UserDetails userDetails){
        var user = userService.findByName(userDetails.getUsername());
        if(userDetails instanceof AppUserDetails &&userDetails.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_USER.name())) ){
            if(!Objects.equals(user.getId(), id)){
                throw new ValidException("Access denied");
            }
        }
    }


}
