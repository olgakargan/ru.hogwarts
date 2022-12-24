package com.example.school.impl;

import com.example.school.exception.NotFoundException;
import com.example.school.exception.UnableToDeleteException;
import com.example.school.exception.UnableToUpdateException;
import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import com.example.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor


public abstract class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    @Override
    public Faculty createFaculty(Faculty faculty) {

        logger.debug("Calling method createFaculty");

            return facultyRepository.save(faculty);

    }

    @Override
    public Faculty editFaculty(Faculty faculty, String message) {
        logger.debug("Calling method editFaculty (faculty = {},message = {}))", faculty,message);

        try {
            return facultyRepository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToUpdateException(e, message);
        }
    }

    @Override
    public void deleteFaculty(long id) {
        logger.debug("Calling method deleteFaculty (facultyId = {})", id);
        try {
            facultyRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException("Faculty", "id", id, e);
        }
    }


    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.debug("Calling method getFacultiesByColor (color = {})", color);
        return facultyRepository.findByColor(color);
    }



    @Override
    public Faculty findFacultyById(long id) {
        logger.debug("Calling method findFaculty (facultyId = {})", id);
        try {
            return facultyRepository.getById(id);
        } catch (Exception e) {
            throw new NotFoundException("Faculty", "id", id, e);
        }
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.debug("Calling method getFacultyStudents");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        logger.debug("Calling method findFacultiesByNameOrColor (color = {}, name = {})",color, name);
        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }

}