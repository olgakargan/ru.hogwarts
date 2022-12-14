package com.example.school.controller;

import com.example.school.model.Faculty;
import com.example.school.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@Valid @RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@Valid @RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/find")
    public Collection<Faculty> findFacultyByColorOrName(@RequestParam(required = false) String color,
                                                        @RequestParam(required = false) String name) {
        return facultyService.findByColorOrName(color, name);
    }
}