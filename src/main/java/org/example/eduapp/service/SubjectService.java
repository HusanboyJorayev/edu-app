package org.example.eduapp.service;

import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.SubjectDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface SubjectService {

    ApiResponse create(SubjectDto.CreateSubject dto);

    ApiResponse get(Long id);

    ApiResponse delete(Long id);

    ApiResponse getAll(Pageable pageable);

    ApiResponse update(Long id, SubjectDto.CreateSubject dto);
}
