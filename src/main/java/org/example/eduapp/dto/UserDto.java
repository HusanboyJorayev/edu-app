package org.example.eduapp.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Long chatId;
    private List<String> roles = new ArrayList<>();
    private List<String> imageUrl = new ArrayList<>();
    private String token;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateUser{
        private Long id;
        private String username;
        private String phone;
        private String email;
        private String password;
        private String firstname;
        private String lastname;
    }
}
