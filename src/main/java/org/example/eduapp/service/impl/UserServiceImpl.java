package org.example.eduapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.UserDto;
import org.example.eduapp.entity.*;
import org.example.eduapp.exceptions.ResourceNotFoundException;
import org.example.eduapp.mapper.AttachmentMapper;
import org.example.eduapp.mapper.UserMapper;
import org.example.eduapp.repository.GroupRepository;
import org.example.eduapp.repository.RoleRepository;
import org.example.eduapp.repository.SubjectRepository;
import org.example.eduapp.repository.UserRepository;
import org.example.eduapp.service.UserService;
import org.example.eduapp.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AttachmentMapper attachmentMapper;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final Utils utils;

    @Override
    public ApiResponse addRoleToUser(Long userId, Long roleId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER IS NOT FOUND"));
        UserRole role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("ROLE IS NOT FOUND"));
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            user.getRole().add(role);
        } else {
            user.setRole(new HashSet<>(Set.of(role)));
        }
        this.userRepository.save(user);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("ROLE ADDED SUCCESSFULLY TO USER")
                .build();
    }

    @Override
    public ApiResponse deleteRoleFromUser(Long userId, Long roleId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER IS NOT FOUND"));
        this.roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("ROLE IS NOT FOUND"));
        this.userRepository.deleteRoleFromUser(userId, roleId);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("DELETE ROLE FROM USER")
                .build();
    }

    @Override
    public ApiResponse getById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER IS NOT FOUND"));
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.userMapper.toDto(user))
                .build();
    }

    @Override
    public ApiResponse getByToken(String token) {
        User user = this.userRepository.getUserByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("USER IS NOT FOUND"));
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.userMapper.toDto(user))
                .build();
    }

    @Override
    public ApiResponse getAll(Pageable pageable) {
        Page<User> page = this.userRepository.findAll(pageable);
        if (!page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.userMapper.dtoList(page.getContent()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public ApiResponse getAllStudents(Pageable pageable) {
        Page<User> page = this.userRepository.getAllStudents(pageable);
        if (!page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.userMapper.dtoList(page.getContent()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public ApiResponse getAllTeachers(Pageable pageable) {
        Page<User> page = this.userRepository.getAllTeacher(pageable);
        if (!page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.userMapper.dtoList(page.getContent()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public ApiResponse getAllParents(Pageable pageable) {
        Page<User> page = this.userRepository.getAllParents(pageable);
        if (!page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.userMapper.dtoList(page.getContent()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public ApiResponse update(Long id, UserDto.UpdateUser dto) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER IS NOT FOUND"));
        User u = this.userRepository.findByUsername(dto.getUsername()).orElse(null);

        if (!utils.checkEmail(dto.getEmail())) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("INVALID EMAIL")
                    .build();
        }
        if (!utils.checkPhoneNumber(dto.getPhone())) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("INVALID PHONE NUMBER")
                    .build();
        }
        if (!user.getUsername().equals(dto.getUsername()) && u != null) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("USERNAME IS ALREADY EXIST")
                    .build();
        }

        this.userMapper.update(user, dto);
        user.setUpdatedAt(LocalDateTime.now());
        this.userRepository.save(user);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .build();
    }

    @Override
    public ApiResponse updateByStudent(Long id, String password, String username) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER IS NOT FOUND"));
        User u = this.userRepository.findByUsername(username).orElse(null);
        if (!user.getUsername().equals(username) && u != null) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("USERNAME IS ALREADY EXIST")
                    .build();
        }
        this.userMapper.updateByStudent(user, password, username);
        user.setUpdatedAt(LocalDateTime.now());
        this.userRepository.save(user);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .build();
    }

    @Override
    public ApiResponse addImage(Long id, MultipartFile file) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER IS NOT FOUND"));
        try {
            Attachment entity = this.attachmentMapper.toEntity(file);
            if (user.getImages() != null && !user.getImages().isEmpty()) {
                user.getImages().add(entity);
            } else {
                user.setImages(new HashSet<>(Set.of(entity)));
            }
            this.userRepository.save(user);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .build();
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public ApiResponse getAllStudentByGroupId(Long groupId, Pageable pageable) {
        Group group = this.groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        Page<User> page = this.userRepository.getAllStudentByGroupId(group.getId(), pageable);
        if (page != null && !page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.userMapper.dtoList(page.getContent()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public ApiResponse getAllTeacherBySubjectId(Long subjectId, Pageable pageable) {
        Subject subject = this.subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("SUBJECT IS NOT FOUND"));
        Page<User> page = this.userRepository.getAllTeacherBySubjectId(subject.getId(), pageable);
        if (page != null && !page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.userMapper.dtoList(page.getContent()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }
}
