package com.example.school.controller;

import com.example.school.impl.FacultyService;
import com.example.school.model.Faculty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping
    public Faculty createFaculty(@Valid @RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@Valid @RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFacultyById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/find")
    public Collection<Faculty> findFacultyByColorOrName(@RequestParam(required = false) String color,
                                                        @RequestParam(required = false) String name) {
        return facultyService.findByColorOrName(color, name);
    }

    @GetMapping("/name/max")
    public String maxLongFacultyName() {
        return facultyService.maxLongFacultyName();
    }

    @GetMapping("/test_stream")
    public List<String> testStream() {
        List<String> measurements = new ArrayList<>();
        long duration;

        Instant start = Instant.now();
        Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        Instant finish = Instant.now();
        duration = Duration.between(start, finish).toMillis();
        measurements.add("Nonparallel - " + duration);

        start = Instant.now();
        Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        finish = Instant.now();
        duration = Duration.between(start, finish).toMillis();
        measurements.add("Parallel - " + duration);

        return measurements;
    }
}