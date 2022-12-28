package com.example.school.impl;


import com.example.school.dto.StudentDto;
import com.example.school.model.Faculty;

import java.util.List;
public interface StudentService {
    StudentDto createStudent(StudentDto studentDto, Faculty faculty);
    StudentDto findStudentById(long id);

    StudentDto createStudent(StudentDto studentDto);

    StudentDto editStudent(StudentDto studentDto);
    void deleteStudentById(long id);
    List<StudentDto> getAllStudents();
    List<StudentDto> findByAge(int age);
    List<StudentDto> findByAgeBetween(int age1, int age2);
    Integer getStudentsCount();
    Float getStudentsAgesAverage();
    double getStudentsAgesAverage2();
    List<StudentDto> getLastStudents(Integer number);
    List<String> getStudentsBeginWithLetter(Character X);

    StudentDto addStudentToFaculty(StudentDto studentDto, Long faculty_id);

    void printStudentsWithThreads1();

    void printStudentsWithThreads2();
}