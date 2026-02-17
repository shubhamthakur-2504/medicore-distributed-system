package com.management.system.authservice.controller;


import com.management.system.authservice.dto.LoginRequestDto;
import com.management.system.authservice.dto.LoginResponseDto;
import com.management.system.authservice.dto.RegisterRequestDto;
import com.management.system.authservice.dto.RegisterResponseDto;
import com.management.system.authservice.service.AuthService;
import com.management.system.authservice.service.UserService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "API for Auth service" )
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "Register user data")
    @PostMapping
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto userDetails){
        RegisterResponseDto user = userService.registerUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Login route")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginData){
        LoginResponseDto user = authService.login(loginData);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Validate access token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateAccess(@RequestHeader("Authorization") String authHeader){
        return authService.validateAccessToken(extractToken(authHeader))? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Logout route")
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String authHeader){
        authService.logout(extractToken(authHeader));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Refresh access token")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshAccess(@RequestBody Map<String, String> body){
        String token = body.get("token");
        LoginResponseDto tokens = authService.refreshAccessToken(token);
        return ResponseEntity.ok().body(tokens);
    }

    @Operation(summary = "get User details")
    @GetMapping("/me")
    public ResponseEntity<RegisterResponseDto> getUserDetails(@RequestHeader("Authorization") String authHeader){
        RegisterResponseDto details = userService.getUser(extractToken(authHeader));
        return ResponseEntity.ok().body(details);
    }

    private String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtException("Missing or invalid Authorization header");
        }
        return header.substring(7);
    }
}
