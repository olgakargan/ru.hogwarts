package com.example.school.impl;

import com.example.school.exception.*;
import com.example.school.model.Faculty;
import com.example.school.model.Student;
import com.example.school.repository.FacultyRepository;
import com.example.school.repository.StudentRepository;
import com.example.school.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static com.example.school.exception.ApiException.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Student createStudent(Student student) {

            return studentRepository.save(student);

    }

    @Override
    public Student addStudentToFaculty(Student student, Long facultyId) {

            Faculty faculty = facultyRepository.getById(facultyId);
            student.setFaculty(faculty);

        return studentRepository.save(student);
    }

    @Override
    public Student editStudent(Student student) {

            return studentRepository.save(student);

    }

    @Override
    public void deleteStudent(long id) {

            studentRepository.deleteById(id);

    }

    @Override
    public Student findStudent(long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new NotFoundException("Student", "id", id);
        }
        return optionalStudent.get();
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        if (max < min) {
            throw new BadRequestException(FIRST_AGE_MORE_THAN_SECOND_ERROR);
        }
        return studentRepository.findByAgeBetween(min, max);
    }
}