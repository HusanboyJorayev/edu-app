package org.example.eduapp.mapper;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.SubjectDto;
import org.example.eduapp.entity.Subject;
import org.example.eduapp.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectMapper {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Subject toEntity(SubjectDto.CreateSubject dto) {
        return Subject.builder()
                .name(dto.getName())
                .build();
    }

    public SubjectDto toDto(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .createdAt(subject.getCreatedAt())
                .updatedAt(subject.getUpdatedAt())
                .build();
    }

    public List<SubjectDto> dtoList(List<Subject> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Subject subject, SubjectDto.CreateSubject dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            subject.setName(dto.getName());
        }
    }
}
