package com.example.Hotel.security;

import com.example.Hotel.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserServiceImpl usersService;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        var user = usersService.findByName(name);
        log.info("user role {}", user.getRoles().toString());
        return new AppUserDetails(user);
    }
}
