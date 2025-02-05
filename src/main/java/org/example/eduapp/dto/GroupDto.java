package org.example.eduapp.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto teacher;
    private SubjectDto subject;
    private List<UserDto> students = new ArrayList<>();


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateGroup {
        private String name;
        private Long subjectId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddTeacher {
        private Long id;
        private Long teacherId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddStudent {
        private Long id;
        private List<Long> studentIds = new ArrayList<>();
    }
}
