package com.startdb.volunteerdb.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startdb.volunteerdb.Enum.GenderEnum;
import com.startdb.volunteerdb.model.Volunteer;

@SpringBootTest
@AutoConfigureMockMvc
public class VolunteerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {}

     @Test
    void testCreateVolunteer_Success() throws Exception {
        Volunteer volunteer = new Volunteer(
                "Ana Cagliari", GenderEnum.FEMININO, 30, "12345678901", "51912345678", "90123450",
                "Porto Alegre", "ana.cagliari@example.com", "Rua A 123");

        mockMvc.perform(post("/volunteers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(volunteer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ana Cagliari"))
                .andExpect(jsonPath("$.email").value("ana.cagliari@example.com"));
    }

    @Test
    void testGetAllVolunteers_Success() throws Exception {
        mockMvc.perform(get("/volunteers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())  
                .andExpect(jsonPath("$").isNotEmpty());  
    }

    @Test
    void testGetVolunteerById_Success() throws Exception {
    
        Long volunteerId = 1L;  
        mockMvc.perform(get("/volunteers/{id}", volunteerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(volunteerId));
    }

    @Test
    void testGetVolunteerById_NotFound() throws Exception {
        Long volunteerId = 999L;  
        mockMvc.perform(get("/volunteers/{id}", volunteerId))
                .andExpect(status().isNotFound());
    }

}

  
