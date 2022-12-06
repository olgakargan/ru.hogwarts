package com.example.school.service;

import com.example.school.model.Faculty;

import javax.validation.constraints.Size;
import java.util.Collection;


public interface FacultyService {

    Faculty createFaculty(Faculty faculty);


    Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> findByColorOrName(String color, @Size(min = 2, max = 30) String name);
}