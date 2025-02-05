package org.example.eduapp.service;

import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.GroupDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface GroupService {

    ApiResponse create(GroupDto.CreateGroup dt);

    ApiResponse addTeacher(GroupDto.AddTeacher teacher);

    ApiResponse addStudent(GroupDto.AddStudent student);

    ApiResponse get(Long id);

    ApiResponse getAll(Pageable pageable);

    ApiResponse update(Long id, GroupDto.CreateGroup dto);

    ApiResponse delete(Long id);

    ApiResponse deleteStudent(Long studentId, Long groupId);

    ApiResponse deleteTeacher(Long teacherId, Long groupId);

    ApiResponse getAllGroupByTeacherId(Long teacherId, Pageable pageable);
}
