package com.example.school;

import com.example.school.controller.FacultyController;
import com.example.school.impl.FacultyServiceImpl;
import com.example.school.impl.StudentServiceImpl;
import com.example.school.model.Faculty;
import com.example.school.repository.FacultyRepository;
import com.example.school.service.AvatarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.example.school.constant.Constant.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest
class FacultyTest {
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarService avatarService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @MockBean
    StudentServiceImpl studentService;

    @Autowired
    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(facultyController)
                .alwaysExpect(status().isOk())
                .alwaysDo(print())
                .build();
    }

    @AfterEach
    void tearDown() {
        mockMvc = null;
    }

    @Test
    void contextLoads() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void postFaculty() throws Exception {
        when(facultyRepository.save(FACULTY_ONE)).thenReturn(FACULTY_ONE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FACULTY_ONE))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(ID_ONE))
                .andExpect(jsonPath("$.name").value(GOOD_NAME1))
                .andExpect(jsonPath("$.color").value(GOOD_COLOR1));
    }

    @Test
    void editFaculty() throws Exception {
        when(facultyRepository.save(FACULTY_ONE)).thenReturn(FACULTY_ONE);
        Faculty faculty = new Faculty(ID_ONE, GOOD_NAME2, GOOD_COLOR2);
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID_ONE))
                .andExpect(jsonPath("$.name").value(GOOD_NAME2))
                .andExpect(jsonPath("$.color").value(GOOD_COLOR2));
    } // todo given when than

    @Test
    void getFacultyInfo() throws Exception {
        when(facultyRepository.findById(ID_ONE)).thenReturn(Optional.of(FACULTY_ONE));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", ID_ONE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(ID_ONE))
                .andExpect(jsonPath("$.name").value(GOOD_NAME1))
                .andExpect(jsonPath("$.color").value(GOOD_COLOR1));
    }

    @Test
    void deleteFaculty() throws Exception {
        doNothing().when(facultyRepository).deleteById(ID_ONE);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/faculty/{id}", ID_ONE));

//        doThrow(UnableToDeleteException.class).when(facultyRepository).deleteById(ID_TWO);
//        mockMvc.perform(MockMvcRequestBuilders
//                .delete("/faculty/{id}", ID_TWO))
//                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll() throws Exception {
        when(facultyRepository.findAll()).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON)
                )

                .andExpect(jsonPath("$.length()").value(faculties.size()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(faculties)));
    }

    @Test
    void findFacultyByColorOrName() throws Exception {
        when(facultyRepository.findFacultiesByColorIgnoreCase(GOOD_COLOR2))
                .thenReturn(List.of(FACULTY_TWO));
        when(facultyRepository.findFacultiesByNameIgnoreCase(GOOD_NAME2))
                .thenReturn(List.of(FACULTY_TWO));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find")
                        .param("name", GOOD_NAME2)
                        .param("color", GOOD_COLOR2)
                        .accept(MediaType.APPLICATION_JSON)
                )

                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(FACULTY_TWO))));
    }

}