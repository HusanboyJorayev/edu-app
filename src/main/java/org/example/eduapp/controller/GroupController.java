package org.example.eduapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.GroupDto;
import org.example.eduapp.service.GroupService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody GroupDto.CreateGroup dt) {
        return this.groupService.create(dt);
    }

    @PostMapping("/addTeacher")
    public ApiResponse addTeacher(@RequestBody GroupDto.AddTeacher teacher) {
        return this.groupService.addTeacher(teacher);
    }

    @PostMapping("/addStudent")
    public ApiResponse addStudent(@RequestBody GroupDto.AddStudent student) {
        return this.groupService.addStudent(student);
    }

    @GetMapping("/get")
    public ApiResponse get(@RequestParam("id") Long id) {
        return this.groupService.get(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.groupService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestParam("id") Long id,
                              @RequestBody GroupDto.CreateGroup dto) {
        return this.groupService.update(id, dto);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam("id") Long id) {
        return this.groupService.delete(id);
    }

    @DeleteMapping("/deleteStudent")
    public ApiResponse deleteStudent(@RequestParam("studentId") Long studentId,
                                     @RequestParam("groupId") Long groupId) {
        return this.groupService.deleteStudent(studentId, groupId);
    }

    @DeleteMapping("/deleteTeacher")
    public ApiResponse deleteTeacher(@RequestParam("teacherId") Long teacherId,
                                     @RequestParam("groupId") Long groupId) {
        return this.groupService.deleteTeacher(teacherId, groupId);
    }

    @GetMapping("/getAllGroupByTeacherId")
    public ApiResponse getAllGroupByTeacherId(@RequestParam("teacherId") Long teacherId,
                                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.groupService.getAllGroupByTeacherId(teacherId, PageRequest.of(page, size));
    }
}
