package com.example.school.controller;

import com.example.school.model.Faculty;
import com.example.school.model.Student;
import com.example.school.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Collection;

@RestController
@RequestMapping("faculty")
@Validated
public class FacultyController {
    private final FacultyService facultyService;
    private FacultyController studentService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping
    public Collection<Faculty> getAll(){
        return facultyService.getAllFaculties();

    }

    @PutMapping
        public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        return ResponseEntity.ok(foundFaculty);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/find")
    public Collection<Faculty> findFacultiesByColor(@Size (min = 2)
            @RequestParam(required = false) String color,
                                                          @Size (min = 2, max = 30)
                                                          @RequestParam(required = false) String name)
            {
        return facultyService.findByColorOrName(color, name);
    }
    @PostMapping("/{facultyId}")
    public Student addStudentToFaculty(@Valid @RequestBody Student student, @PathVariable Long facultyId) {
        return studentService.addStudentToFaculty(student, facultyId);
    }
}