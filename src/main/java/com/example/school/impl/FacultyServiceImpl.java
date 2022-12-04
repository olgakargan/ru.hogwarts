package com.example.school.impl;

import com.example.school.exception.NotFoundException;
import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import com.example.school.service.FacultyService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {

            return repository.save(faculty);

    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
                   return repository.save(faculty);
            }

    @Override
    public void deleteFaculty(long id) {

            repository.deleteById(id);

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

        return repository.findFacultiesByColorOrNameIgnoreCase(color, name);
        }


}