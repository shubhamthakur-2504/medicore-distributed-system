package com.management.system.authservice.mapper;

import com.management.system.authservice.dto.LoginResponseDto;
import com.management.system.authservice.dto.RegisterRequestDto;
import com.management.system.authservice.dto.RegisterResponseDto;
import com.management.system.authservice.model.User;

public class UserMapper {

    public static RegisterResponseDto toRegisterDto(User user){
        RegisterResponseDto dto = new RegisterResponseDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }

    public static User toModel (RegisterRequestDto request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setPassword(request.getPassword());

        return user;
    }

    public static LoginResponseDto toLoginDto(String access, String refresh){
        LoginResponseDto response = new LoginResponseDto();
        response.setAccessToken(access);
        response.setRefreshToken(refresh);

        return response;
    }
}
