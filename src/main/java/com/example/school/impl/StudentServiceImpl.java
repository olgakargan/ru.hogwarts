package com.example.school.impl;

import com.example.school.exception.*;
import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import com.example.school.service.StudentService;
import org.springframework.stereotype.Service;


import java.util.Collection;

import static com.example.school.exception.ApiException.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }
    @Override
    public Student createStudent(Student student) {
        try {
            return repository.save(student);
        } catch (RuntimeException e) {
            throw new UnableToCreateException(UNABLE_TO_CREATE, e);
        }
    }

    @Override
    public Student addStudentToFaculty(Student student, Long facultyId) {
        return repository.save(student);
    }

    @Override
    public Student editStudent(Student student) {
        try {
            return repository.save(student);
        } catch (RuntimeException e) {
            throw new UnableToUpdateException(UNABLE_TO_UPDATE, e);
        }
    }
    @Override
    public void deleteStudent(long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException("Student", "id", id);
        }
    }
    @Override
    public Student findStudent(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student", "id", id));
    }
    @Override
    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }
    @Override
    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }
    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        if (max < min) {
            throw new BadRequestException(FIRST_AGE_MORE_THAN_SECOND_ERROR);
        }
        return repository.findByAgeBetween(min, max);
    }
    @Override
    public Integer getStudentsCount() {
        return repository.countAllById();
    }
    @Override
    public Float getStudentsAgesAverage() {
        return repository.averageAgesStudents();
    }

    @Override
    public Collection<Student> getLastStudents(Integer number) {
        return repository.getLastFifeStudents(number);
    }
}