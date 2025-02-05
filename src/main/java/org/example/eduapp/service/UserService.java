package org.example.eduapp.service;


import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface UserService {

    ApiResponse addRoleToUser(Long userId, Long roleId);

    ApiResponse deleteRoleFromUser(Long userId, Long roleId);

    ApiResponse getById(Long id);

    ApiResponse getByToken(String token);

    ApiResponse getAll(Pageable pageable);

    ApiResponse getAllStudents(Pageable pageable);

    ApiResponse getAllTeachers(Pageable pageable);

    ApiResponse getAllParents(Pageable pageable);

    ApiResponse update(Long id, UserDto.UpdateUser dto);

    ApiResponse updateByStudent(Long id, String password, String username);

    ApiResponse addImage(Long id, MultipartFile file);

    ApiResponse getAllStudentByGroupId(Long groupId, Pageable pageable);

    ApiResponse getAllTeacherBySubjectId(Long subjectId, Pageable pageable);
}
