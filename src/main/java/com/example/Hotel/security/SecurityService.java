package com.example.Hotel.security;


import com.example.Hotel.dto.userDto.*;
import com.example.Hotel.entity.RefreshToken;
import com.example.Hotel.entity.Users;
import com.example.Hotel.entity.enumurated.RoleType;
import com.example.Hotel.exception.RefreshTokenException;
import com.example.Hotel.exception.ValidException;
import com.example.Hotel.repository.UserRepository;
import com.example.Hotel.security.jwt.JwtUtils;
import com.example.Hotel.service.impl.ListenerEventServiceImpl;
import com.example.Hotel.service.impl.RefreshTokenService;
import com.example.Hotel.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserServiceImpl usersService;
    private final UserRepository userRepository;




    public AuthResponse authenticateUser(UserRequestDto upsertUserDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(upsertUserDTO.getName(), upsertUserDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        System.out.println(roles);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        String token = jwtUtils.generateJwtToken(userDetails);
        log.info(token+"token");
        return AuthResponse.builder().id(userDetails.getId()).token(token).refreshToken(refreshToken.getToken())
                .name(userDetails.getUsername()).roles(roles).build();
    }
    public UserResponseDto register(UserRequestDto userRequestDto){

        return usersService.save(userRequestDto);
    }
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String requestRefresh= refreshTokenRequest.getRefreshToken();
        return refreshTokenService.findByRefreshToken(requestRefresh).map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userid->{
                    Users tokenOwner = userRepository.findById(userid).orElseThrow(()->new RefreshTokenException("Exception trying to get token for userId: {}"+ userid));
                    String token = jwtUtils.generateTokenFromUserName(tokenOwner.getName());
                    return new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userid).getToken());
                }).orElseThrow(()->new RefreshTokenException(requestRefresh,"Refresh token not found"));
    }
    public void isUser(Users users) {
        if(isUserRole()&& !Objects.equals(getCurrentId(), users.getId())){
            throw new ValidException("Access denied");
        }
        if(isModerator() && users.getRoles().stream().anyMatch(role -> role.getAuthority().equals(RoleType.ROLE_ADMIN))){
            throw new ValidException("Access denied");
        }
    }
    public void logout(){
        var currentPrincipal = getAuthentication().getPrincipal();
        if(currentPrincipal instanceof AppUserDetails userDetails){
            Long userId= userDetails.getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public boolean isAdmin(){
        System.out.println(getAuthentication().getName());
        return getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> RoleType.ROLE_ADMIN.name().equals(grantedAuthority.getAuthority()));
    }
    public boolean isModerator(){
        return getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_MODERATOR".equals(grantedAuthority.getAuthority()));
    }
    public boolean isUserRole(){
        return getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_USER".equals(grantedAuthority.getAuthority()));
    }
    public Long getCurrentId(){
        Authentication authentication = getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof AppUserDetails details){
            return details.getId();
        }
        return null;
    }

}

