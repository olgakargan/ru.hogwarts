package com.example.school.service;


import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Student findStudent(long id) {
       return repository.findById(id).get();
            }

    public Student editStudent(Student student) {
        return repository.save(student);
    }


    public void deleteStudent(long id) {
        repository.deleteById(id);

    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }
}