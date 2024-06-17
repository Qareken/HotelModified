package com.example.Hotel.security;

import com.example.Hotel.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserServiceImpl usersService;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return new AppUserDetails(usersService.findByName(name));
    }
}
