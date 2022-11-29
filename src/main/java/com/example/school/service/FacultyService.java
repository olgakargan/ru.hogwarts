package com.example.school.service;

import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {

        return repository.save(faculty);

    }

    public Faculty findFaculty(Long id) {
        return repository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {

        return repository.save(faculty);
    }

    public void deleteFaculty(long id) {
        repository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }

    public List<Faculty> findByColor(String color) {
        return repository.findByColor(color);
    }
}