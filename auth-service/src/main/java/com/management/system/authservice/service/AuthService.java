package com.management.system.authservice.service;

import com.management.system.authservice.dto.LoginRequestDto;
import com.management.system.authservice.dto.LoginResponseDto;
import com.management.system.authservice.exception.InvalidData;
import com.management.system.authservice.mapper.UserMapper;
import com.management.system.authservice.model.User;
import com.management.system.authservice.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private static final String DUMMY_HASH = "$2a$10$86Is9hNQuS7fXm8yXY.fG.S6N7.fG.S6N7.fG.S6N7.fG.S6N7";

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDto login(LoginRequestDto requestDto){
        User user = userService.findByEmail(requestDto.getEmail()).orElse(null);
        boolean passwordMatch = passwordEncoder.matches(requestDto.getPassword(), (user != null)?
                user.getPassword() : DUMMY_HASH);
        if(user == null || !passwordMatch){
            throw new InvalidData("Email or Password is wrong");
        }
        String accessToken = jwtUtils.generateToken(user.getEmail(), user.getRole().toString(), false);
        String refreshToken = jwtUtils.generateToken(user.getEmail(), user.getRole().toString(), true);

        userService.saveRefreshToken(user.getId(), refreshToken);

        return UserMapper.toLoginDto(accessToken, refreshToken);
    }

    public LoginResponseDto refreshAccessToken(String token){
        jwtUtils.validateToken(token, true);

        String email = jwtUtils.extractEmail(token, true);

        User user = userService.findByEmail(email).orElseThrow(()-> new InvalidData("User no longer exist"));

        if(!token.equals(user.getRefreshToken())){
            throw new InvalidData("Invalid or Rotated refresh token");
        }

        String accessToken = jwtUtils.generateToken(user.getEmail(), user.getRole().toString(), false);
        String refreshToken = jwtUtils.generateToken(user.getEmail(), user.getRole().toString(), true);

        userService.saveRefreshToken(user.getId(), refreshToken);

        return UserMapper.toLoginDto(accessToken, refreshToken);
    }

    public boolean validateAccessToken(String token){
        try {
            jwtUtils.validateToken(token, false);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public void logout(String token){
        jwtUtils.validateToken(token, false);
        userService.clearToken(jwtUtils.extractEmail(token, false));

    }

    public String getEmailFromToken(String token){
        jwtUtils.validateToken(token, false);
        return jwtUtils.extractEmail(token, false);
    }
}
