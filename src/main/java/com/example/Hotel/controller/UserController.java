package com.example.Hotel.controller;

import com.example.Hotel.dto.*;
import com.example.Hotel.dto.userDto.*;
import com.example.Hotel.security.SecurityService;
import com.example.Hotel.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl userService;
    private final SecurityService service;
    private final PasswordEncoder passwordEncoder;
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<PageResponseDto<UserResponseDto>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findALL(pageRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody UserRequestDto upsertUserDTO){
        log.info(upsertUserDTO+"warn");
        return ResponseEntity.ok(service.authenticateUser(upsertUserDTO));
    }
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto){
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return ResponseEntity.ok(service.register(userRequestDto));
    }
    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(service.refreshToken(refreshTokenRequest));
    }
    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        service.logout();
        return ResponseEntity.ok(new SimpleResponse("user logout. Username is: "+userDetails.getUsername()));
    }
    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/by-id/{id}")
    public ResponseEntity<UserResponseDto> updateById(@RequestBody @Valid UserRequestDto userRequestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return ResponseEntity.ok(userService.update(userRequestDto, id));
    }
}
