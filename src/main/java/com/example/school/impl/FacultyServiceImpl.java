package com.example.school.impl;

import com.example.school.exception.*;
import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import com.example.school.service.FacultyService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static com.example.school.exception.ApiException.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        try {
            return repository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToCreateException(UNABLE_TO_CREATE, e);
        }
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        try {
            return repository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToUpdateException(UNABLE_TO_UPDATE, e);
        }
    }

    @Override
    public void deleteFaculty(long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException("Faculty", "id", id);
        }
    }

    @Override
    public Faculty findFaculty(long id) {
        Optional<Faculty> optionalFaculty = repository.findById(id);
        if (optionalFaculty.isEmpty()) {
            throw new NotFoundException("Faculty", "id", id);
        }
        return optionalFaculty.get();
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        if (color == null && name == null) {
            throw new BadRequestException(ALL_FIELDS_ARE_NULL);
        }

        if (name != null) {
            return repository.findFacultiesByNameIgnoreCase(name);
        } else {
            return repository.findFacultiesByColorIgnoreCase(color);
        }
    }

}