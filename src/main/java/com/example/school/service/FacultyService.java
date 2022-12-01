package com.example.school.service;

import com.example.school.exception.NotFoundException;
import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {

        return repository.save(faculty);
    }


    public Faculty findFaculty(long id) {
        Optional<Faculty> optionalFaculty = repository.findById(id);
        if (optionalFaculty.isEmpty()) {
            throw new NotFoundException();
        }
        return optionalFaculty.get();
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