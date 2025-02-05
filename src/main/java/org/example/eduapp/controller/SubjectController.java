package org.example.eduapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.dto.ApiResponse;
import org.example.eduapp.dto.SubjectDto;
import org.example.eduapp.service.SubjectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody SubjectDto.CreateSubject dto) {
        return this.subjectService.create(dto);
    }

    @GetMapping("/get")
    public ApiResponse get(@RequestParam("id") Long id) {
        return this.subjectService.get(id);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam("id") Long id) {
        return this.subjectService.delete(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return this.subjectService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestParam("id") Long id,
                              @RequestBody SubjectDto.CreateSubject dto) {
        return this.subjectService.update(id, dto);
    }
}
