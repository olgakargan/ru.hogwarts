package com.example.school.impl;

import com.example.school.exception.NotFoundException;
import com.example.school.exception.UnableToDeleteException;
import com.example.school.exception.UnableToUpdateException;
import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import jdk.internal.module.ModulePath;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.module.ModuleReference;
import java.util.Collection;
import java.util.Set;

import static com.example.school.impl.StudentServiceImpl.CREATED;

@Service
@RequiredArgsConstructor
@Log4j2
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

            return facultyRepository.save(faculty);

    }

    @Override
    public Faculty editFaculty(Faculty faculty, String message) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            return facultyRepository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToUpdateException(e, message);
        }
    }

    @Override
    public void deleteFaculty(long id) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            facultyRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException("Faculty", "id", id, e);
        }
    }

    @Override
    public Faculty findFaculty(long id, Exception e) {
        return null;
    }

    @Override
    public Set<ModuleReference> getAllFaculties(ModulePath facultyRepository) {
        return null;
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name, FacultyRepository facultyRepository) {
        return null;
    }

    @Override
    public Faculty createFaculty(Faculty faculty, String message) {
        return null;
    }

    @Override
    public Faculty findFacultyById(long id) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            return facultyRepository.getById(id);
        } catch (Exception e) {
            throw new NotFoundException("Faculty", "id", id, e);
        }
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }

}