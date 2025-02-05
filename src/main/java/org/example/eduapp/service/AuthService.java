package org.example.eduapp.service;

import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.LoginDto;
import org.example.eduapp.dto.RegisterDto;
import org.springframework.stereotype.Component;

@Component
public interface AuthService {

    ApiResponse register(RegisterDto registerDto);

    ApiResponse login(LoginDto loginDto);
}
