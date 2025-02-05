package org.example.eduapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.UserDto;
import org.example.eduapp.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/addRoleToUser")
    public ApiResponse addRoleToUser(@RequestParam("userId") Long userId,
                                     @RequestParam("roleId") Long roleId) {
        return this.userService.addRoleToUser(userId, roleId);
    }

    @DeleteMapping("/deleteRoleFromUser")
    public ApiResponse deleteRoleFromUser(@RequestParam("userId") Long userId,
                                          @RequestParam("roleId") Long roleId) {
        return this.userService.deleteRoleFromUser(userId, roleId);
    }

    @GetMapping("/getById")
    public ApiResponse getById(@RequestParam("id") Long id) {
        return this.userService.getById(id);
    }

    @GetMapping("/getByToken")
    public ApiResponse getByToken(@RequestParam("token") String token) {
        return this.userService.getByToken(token);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.userService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("/getAllStudents")
    public ApiResponse getAllStudents(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.userService.getAllStudents(PageRequest.of(page, size));
    }

    @GetMapping("/getAllTeachers")
    public ApiResponse getAllTeachers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.userService.getAllTeachers(PageRequest.of(page, size));
    }

    @GetMapping("/getAllParents")
    public ApiResponse getAllParents(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.userService.getAllParents(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestParam("id") Long id,
                              @RequestBody UserDto.UpdateUser dto) {
        return this.userService.update(id, dto);
    }

    @PutMapping("/updateByStudent")
    public ApiResponse updateByStudent(@RequestParam("id") Long id,
                                       @RequestParam("password") String password,
                                       @RequestParam("username") String username) {
        return this.userService.updateByStudent(id, password, username);
    }

    @PostMapping("/addImage")
    public ApiResponse addImage(@RequestParam("id") Long id,
                                @RequestParam("file") MultipartFile file) {
        return this.userService.addImage(id, file);
    }

    @GetMapping("/getAllStudentByGroupId")
    public ApiResponse getAllStudentByGroupId(@RequestParam("groupId") Long groupId,
                                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.userService.getAllStudentByGroupId(groupId, PageRequest.of(page, size));
    }

    @GetMapping("/getAllTeacherBySubjectId")
    public ApiResponse getAllTeacherBySubjectId(@RequestParam("subjectId") Long subjectId,
                                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.userService.getAllTeacherBySubjectId(subjectId, PageRequest.of(page, size));
    }
}
