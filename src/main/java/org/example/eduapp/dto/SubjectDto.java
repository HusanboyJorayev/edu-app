package org.example.eduapp.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {
    private Long id;
    private String name;
    private Set<UserDto> teacher = new HashSet<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateSubject {
        private String name;
    }
}
