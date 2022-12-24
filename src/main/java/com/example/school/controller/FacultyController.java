package com.example.school.controller;

import com.example.school.model.Faculty;
import com.example.school.impl.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("faculty")
@RequiredArgsConstructor
@Profile("!test")
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping
    public Faculty createFaculty(@Valid @RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@Valid @RequestBody Faculty faculty, String message) {
        return facultyService.editFaculty(faculty, message);
    }

    @GetMapping("/{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFacultyById(id);
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