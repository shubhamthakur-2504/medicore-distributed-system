package com.management.system.authservice.service;

import com.management.system.authservice.dto.RegisterRequestDto;
import com.management.system.authservice.dto.RegisterResponseDto;
import com.management.system.authservice.exception.EmailAlreadyExistsException;
import com.management.system.authservice.exception.ServerErrorException;
import com.management.system.authservice.mapper.UserMapper;
import com.management.system.authservice.model.User;
import com.management.system.authservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public RegisterResponseDto registerUser(RegisterRequestDto requestDto){
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new EmailAlreadyExistsException();
        }
        User user = userRepository.save(UserMapper.toModel(requestDto));
        return UserMapper.toRegisterDto(user);
    }

    @Transactional
    public void saveRefreshToken(UUID id, String token){
        User user = userRepository.findById(id).orElseThrow(ServerErrorException::new);
        user.setRefreshToken(token);
    }

    @Transactional
    public void clearToken(String email){
        User user = userRepository.findByEmail(email).orElseThrow(ServerErrorException::new);
        user.setRefreshToken(null);
    }

    public RegisterResponseDto getUser(String email){
        User user = userRepository.findByEmail(email).orElseThrow(ServerErrorException::new);
        return UserMapper.toRegisterDto(user);
    }
}
