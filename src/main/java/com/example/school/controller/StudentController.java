package com.example.school.controller;

import com.example.school.dto.StudentDto;
import com.example.school.impl.StudentService;
import com.example.school.model.Faculty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    //todo everything should work correctly when the database is empty
    @PostMapping
    public StudentDto createStudent(@Valid @RequestBody StudentDto studentDto, Faculty faculty) {
        return studentService.createStudent(studentDto, faculty);
    }
    @PostMapping("/{faculty_id}")
    public StudentDto addStudentToFaculty(@Valid @RequestBody StudentDto studentDto,
                                          @PathVariable Long faculty_id) {
        return studentService.addStudentToFaculty(studentDto, faculty_id);
    }
    @PutMapping
    public StudentDto editStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.editStudent(studentDto);
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
    @GetMapping("/average_ages2")
    public double getAverageAgesStudents2() {
        return studentService.getStudentsAgesAverage2();
    }
    @GetMapping("/last")
    public List<StudentDto> getLastStudents(@RequestParam Integer number) {
        return studentService.getLastStudents(number);
    }

    @GetMapping("/start/{firstChar}")
    public List<String> getStudentsBeginWithLetter(@PathVariable Character firstChar) {
        return studentService.getStudentsBeginWithLetter(firstChar);
    }

    @GetMapping("/print1")
    public void printStudentsWithThreads1() {
        studentService.printStudentsWithThreads1();
    }

    @GetMapping("/print2")
    public void printStudentsWithThreads2() {
        studentService.printStudentsWithThreads2();
    }
}