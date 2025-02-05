package org.example.eduapp.mapper;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.RegisterDto;
import org.example.eduapp.dto.UserDto;
import org.example.eduapp.entity.Attachment;
import org.example.eduapp.entity.Token;
import org.example.eduapp.entity.User;
import org.example.eduapp.entity.UserRole;
import org.example.eduapp.repository.RoleRepository;
import org.example.eduapp.repository.TokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    public User toEntity(RegisterDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .build();
    }

    public UserDto toDto(User user) {
        List<UserRole> roleList = this.roleRepository.findByUserId(user.getId());
        Token token = this.tokenRepository.findByUserId(user.getId()).orElse(null);
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(roleList != null && !roleList.isEmpty() ?
                        roleList.stream().map(UserRole::getName).toList() : new ArrayList<>())
                .token(token != null ? token.getToken() : null)
                .imageUrl(user.getImages() != null && !user.getImages().isEmpty() ?
                        user.getImages().stream().map(Attachment::getCloudPath).toList() : new ArrayList<>())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public List<UserDto> dtoList(List<User> users) {
        if (users != null && !users.isEmpty()) {
            return users.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(User user, UserDto.UpdateUser dto) {
        if (dto == null) {
            return;
        }
        if (dto.getFirstname() != null && !dto.getFirstname().trim().isEmpty()) {
            user.setFirstname(dto.getFirstname());
        }
        if (dto.getLastname() != null && !dto.getLastname().trim().isEmpty()) {
            user.setLastname(dto.getLastname());
        }
        if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            user.setEmail(dto.getEmail());
        }
    }

    public void updateByStudent(User user, String password, String username) {
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (username != null && !username.trim().isEmpty()) {
            user.setUsername(username);
        }
    }
}
