package com.example.school.impl;

import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import jdk.internal.module.ModulePath;

import java.lang.module.ModuleReference;
import java.util.Collection;
import java.util.Set;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id, Exception e);

    Set<ModuleReference> getAllFaculties(ModulePath facultyRepository);

    Collection<Faculty> findByColorOrName(String color, String name, FacultyRepository facultyRepository);

    Faculty createFaculty(Faculty faculty, String message);

    Collection<Faculty> getFacultiesByColor(String color);

    Faculty findFacultyById(long id);

    Faculty editFaculty(Faculty faculty, String message);

    void deleteFaculty(long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> findByColorOrName(String color, String name);
}