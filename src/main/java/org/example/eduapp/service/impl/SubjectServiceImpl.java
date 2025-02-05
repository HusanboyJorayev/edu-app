package org.example.eduapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.SubjectDto;
import org.example.eduapp.entity.Subject;
import org.example.eduapp.exceptions.ResourceNotFoundException;
import org.example.eduapp.mapper.SubjectMapper;
import org.example.eduapp.repository.SubjectRepository;
import org.example.eduapp.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public ApiResponse create(SubjectDto.CreateSubject dto) {
        Subject entity = this.subjectMapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        Subject save = this.subjectRepository.save(entity);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.subjectMapper.toDto(save))
                .build();
    }

    @Override
    public ApiResponse get(Long id) {
        Subject subject = this.subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SUBJECT IS NOT FOUND"));
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.subjectMapper.toDto(subject))
                .build();
    }

    @Override
    public ApiResponse delete(Long id) {
        Subject subject = this.subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SUBJECT IS NOT FOUND"));
        this.subjectRepository.delete(subject);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("DELETED")
                .build();
    }

    @Override
    public ApiResponse getAll(Pageable pageable) {
        Page<Subject> page = this.subjectRepository.findAll(pageable);
        if (!page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.subjectMapper.dtoList(page.getContent()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public ApiResponse update(Long id, SubjectDto.CreateSubject dto) {
        Subject subject = this.subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SUBJECT IS NOT FOUND"));
        this.subjectRepository.delete(subject);
        this.subjectMapper.update(subject, dto);
        subject.setUpdatedAt(LocalDateTime.now());
        Subject save = this.subjectRepository.save(subject);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.subjectMapper.toDto(save))
                .build();
    }
}
