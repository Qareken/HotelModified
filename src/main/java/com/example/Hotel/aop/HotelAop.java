package com.example.Hotel.aop;

import com.example.Hotel.dto.HotelRequestDto;
import com.example.Hotel.exception.ValidException;
import com.example.Hotel.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class HotelAop {
    private final SecurityService service;
    @SneakyThrows
    @Around("execution(* com.example.Hotel.service.impl.HotelServiceImpl.save(..)) ||execution(* com.example.Hotel.service.impl.HotelServiceImpl.update(..))" +
            "|| execution(* com.example.Hotel.service.impl.HotelServiceImpl.deleteById(..))")
    public Object methodSaveOrUpdateOrDelete(ProceedingJoinPoint joinPoint){
        if(!service.isAdmin()){
            throw new ValidException("Access denied");
        }
        return joinPoint.proceed();
    }
}
