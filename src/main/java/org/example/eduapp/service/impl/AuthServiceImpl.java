package org.example.eduapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.LoginDto;
import org.example.eduapp.dto.RegisterDto;
import org.example.eduapp.entity.Token;
import org.example.eduapp.entity.User;
import org.example.eduapp.entity.UserRole;
import org.example.eduapp.exceptions.ResourceNotFoundException;
import org.example.eduapp.mapper.UserMapper;
import org.example.eduapp.repository.RoleRepository;
import org.example.eduapp.repository.TokenRepository;
import org.example.eduapp.repository.UserRepository;
import org.example.eduapp.service.AuthService;
import org.example.eduapp.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponse register(RegisterDto registerDto) {
        User user = this.userRepository.findByUsername(registerDto.getUsername()).orElse(null);
        if (user != null) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("THIS USERNAME ALREADY EXISTS")
                    .build();
        }
        UserRole userRole = this.roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new ResourceNotFoundException("ROLE IS NOT FOUND"));

        User entity = this.userMapper.toEntity(registerDto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setRole(new HashSet<>(Set.of(userRole)));
        User saveUser = this.userRepository.save(entity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(saveUser.getUsername());
        String generateToken = jwtUtil.generateToken(userDetails.getUsername());
        Token token = Token.builder()
                .token(generateToken)
                .type("BEARER")
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(15))
                .user(saveUser)
                .build();

        this.tokenRepository.save(token);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.userMapper.toDto(saveUser))
                .build();
    }

    @Override
    public ApiResponse login(LoginDto loginDto) {
        User user = this.userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("USERNAME IS NOT FOUND"));

        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            authenticationManager.authenticate(
                    authentication
            );

            Token token = this.tokenRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("TOKEN IS NOT FOUND"));
            if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                String userToken = jwtUtil.generateToken(userDetails.getUsername());
                token.setToken(userToken);
                token.setCreatedAt(LocalDateTime.now());
                token.setExpiredAt(LocalDateTime.now().plusDays(15));
                this.tokenRepository.save(token);
            }
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .data(this.userMapper.toDto(user))
                    .build();

        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("INVALID PASSWORD")
                    .build();
        }
    }
}
