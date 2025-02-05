package org.example.eduapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.GroupDto;
import org.example.eduapp.entity.Group;
import org.example.eduapp.entity.Subject;
import org.example.eduapp.entity.User;
import org.example.eduapp.exceptions.ResourceNotFoundException;
import org.example.eduapp.mapper.GroupMapper;
import org.example.eduapp.repository.GroupRepository;
import org.example.eduapp.repository.SubjectRepository;
import org.example.eduapp.repository.UserRepository;
import org.example.eduapp.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public ApiResponse create(GroupDto.CreateGroup dt) {
        Subject subject = this.subjectRepository.findById(dt.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("SUBJECT IS NOT FOUND"));
        Group entity = this.groupMapper.toEntity(dt);
        entity.setSubject(subject);
        entity.setCreatedAt(LocalDateTime.now());
        this.groupRepository.save(entity);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("CREATED")
                .build();
    }

    @Override
    public ApiResponse addTeacher(GroupDto.AddTeacher teacher) {
        User user = this.userRepository.findById(teacher.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("TEACHER IS NOT FOUND"));
        Group group = this.groupRepository.findById(teacher.getId())
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        if (group.getTeacher() != null && !group.getTeacher().isEmpty()) {
            group.getTeacher().add(user);
        } else {
            group.setTeacher(new HashSet<>(Set.of(user)));
        }
        this.groupRepository.save(group);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .build();
    }

    @Override
    public ApiResponse addStudent(GroupDto.AddStudent student) {
        Group group = this.groupRepository.findById(student.getId())
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        List<User> list = this.userRepository.findAllById(student.getStudentIds());
        if (!list.isEmpty()) {
            for (User userStudent : list) {
                if (group.getStudent() != null && !group.getStudent().isEmpty()) {
                    group.getStudent().add(userStudent);
                } else {
                    group.setStudent(new HashSet<>(Set.of(userStudent)));
                }
                this.groupRepository.save(group);
            }
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("STUDENT IS NOT FOUND")
                .build();
    }

    @Override
    public ApiResponse get(Long id) {
        Group group = this.groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.groupMapper.toDto(group))
                .build();
    }

    @Override
    public ApiResponse getAll(Pageable pageable) {
        Page<Group> page = this.groupRepository.findAll(pageable);
        if (!page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.groupMapper.dtoList(page.stream().toList()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public ApiResponse update(Long id, GroupDto.CreateGroup dto) {
        Group group = this.groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        this.groupMapper.update(group, dto);
        group.setUpdatedAt(LocalDateTime.now());
        Group save = this.groupRepository.save(group);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(this.groupMapper.toDto(save))
                .build();
    }

    @Override
    public ApiResponse delete(Long id) {
        Group group = this.groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        this.groupRepository.delete(group);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .build();
    }

    @Override
    public ApiResponse deleteStudent(Long studentId, Long groupId) {
        Group group = this.groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        User user = this.userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT IS NOT FOUND"));
        this.groupRepository.deleteStudent(user.getId(), group.getId());
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("DELETED")
                .build();
    }

    @Override
    public ApiResponse deleteTeacher(Long teacherId, Long groupId) {
        Group group = this.groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("GROUP IS NOT FOUND"));
        User user = this.userRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT IS NOT FOUND"));
        this.groupRepository.deleteTeacher(teacherId, groupId);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("DELETED")
                .build();
    }

    @Override
    public ApiResponse getAllGroupByTeacherId(Long teacherId, Pageable pageable) {
        User teacher = this.userRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT IS NOT FOUND"));
        Page<Group> page = this.groupRepository.getAllGroupByTeacherId(teacherId, pageable);
        if (page != null && !page.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(this.groupMapper.dtoList(page.stream().toList()))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(new ArrayList<>())
                .build();
    }
}
