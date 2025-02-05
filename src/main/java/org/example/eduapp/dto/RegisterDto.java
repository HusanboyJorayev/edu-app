package org.example.eduapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {
    @NotBlank(message = "username cannot be null or empty")
    private String username;
    @NotBlank(message = "password cannot be null or empty")
    private String password;
    @NotBlank(message = "firstname cannot be null or empty")
    private String firstname;
    @NotBlank(message = "lastname cannot be null or empty")
    private String lastname;
}
