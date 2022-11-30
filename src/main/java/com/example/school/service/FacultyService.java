package com.example.school.service;

import com.example.school.exception.NotFoundException;
import com.example.school.exception.UnableToCreateException;
import com.example.school.exception.UnableToDeleteException;
import com.example.school.exception.UnableToUpdateException;
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
        try {
            return repository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToCreateException();
        }
    }

    public Faculty findFaculty(long id) {
        Optional<Faculty> optionalFaculty = repository.findById(id);
        if (optionalFaculty.isEmpty()) {
            throw new NotFoundException();
        }
        return optionalFaculty.get();
    }

    public Faculty editFaculty(Faculty faculty) {
        try {
            return repository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToUpdateException();
        }
    }

    public void deleteFaculty(long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException();
        }
    }
    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }
    public List<Faculty> findByColor(String color) {
        return repository.findByColor(color);
    }
}