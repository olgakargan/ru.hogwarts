package com.example.school.controller;

import com.example.school.dto.StudentDto;
import com.example.school.impl.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Profile("!test")
public class StudentController {
    private final StudentService studentService;

    //todo everything should work correctly when the database is empty
    @PostMapping
    public StudentDto createStudent(@Valid @RequestBody StudentDto studentDto, String message) {
        return studentService.createStudent(studentDto, message);
    }

    @PostMapping("/{faculty_id}")
    public StudentDto addStudentToFaculty(@Valid @RequestBody StudentDto studentDto,
                                          @PathVariable Long faculty_id) {
        return studentService.addStudentToFaculty(studentDto, faculty_id);
    }

    @PutMapping
    public StudentDto editStudent(@Valid @RequestBody StudentDto studentDto, String message) {
        return studentService.editStudent(studentDto, message);
    }

    @GetMapping("/{id}")
    public StudentDto getStudentInfo(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping // http://localhost:8080/student
    public List<StudentDto> getAll() {
        return studentService.getAllStudents();
    }

    @GetMapping("/findby/{age}")
    public List<StudentDto> findStudentsByAge(@PathVariable int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/findby/age")
    public List<StudentDto> findStudentsByAge(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/count")
    public Integer getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping("/average_ages")
    public Float getAverageAgesStudents() {
        return studentService.getStudentsAgesAverage();
    }

    @GetMapping("/last")
    public List<StudentDto> getLastStudents(@RequestParam Integer number) {
        return studentService.getLastStudents(number);
    }
}
