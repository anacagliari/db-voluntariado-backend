package com.startdb.volunteerdb.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.service.VolunteerService;

class VolunteerControllerMockTest {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(volunteerController).build();
    }

    @Test
    void testCreateVolunteer_Success() throws Exception {
        Volunteer volunteer = new Volunteer(
            "Ana",
            "Feminino",
            25,
            "12345678901",
            "123456789",
            "12345-678",
            "Cidade X",
            "ana@email.com",
            "Endereço 1",
            Arrays.asList("Participação em Atividades")
        );

        when(volunteerService.createVolunteer(any(Volunteer.class))).thenReturn(volunteer);

        mockMvc.perform(post("/volunteers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(volunteer)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Ana"));

        verify(volunteerService).createVolunteer(any(Volunteer.class));
    }

    @Test
    void testCreateVolunteer_BadRequest() throws Exception {
        when(volunteerService.createVolunteer(any(Volunteer.class))).thenThrow(new IllegalArgumentException());

        Volunteer invalidVolunteer = new Volunteer();

        mockMvc.perform(post("/volunteers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidVolunteer)))
            .andExpect(status().isBadRequest());

        verify(volunteerService).createVolunteer(any(Volunteer.class));
    }

    @Test
    void testGetAllVolunteers_Success() throws Exception {
        List<Volunteer> volunteers = Arrays.asList(
            new Volunteer("Ana", "Feminino", 25, "12345678901", "123456789", "12345-678", "Cidade X", "ana@email.com", "Endereço 1", Arrays.asList("Atividades")),
            new Volunteer("Suene", "Feminino", 30, "98765432101", "987654321", "54321-678", "Cidade Y", "suene@email.com", "Endereço 2", Arrays.asList("Tarefas"))
        );

        when(volunteerService.getAllVolunteers()).thenReturn(volunteers);

        mockMvc.perform(get("/volunteers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Ana"))
            .andExpect(jsonPath("$[1].name").value("Suene"));

        verify(volunteerService).getAllVolunteers();
    }

    @Test
    void testGetAllVolunteers_NotFound() throws Exception {
        when(volunteerService.getAllVolunteers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/volunteers"))
            .andExpect(status().isNotFound());

        verify(volunteerService).getAllVolunteers();
    }

    @Test
    void testGetVolunteerById_Success() throws Exception {
        Volunteer volunteer = new Volunteer("Ana", "Feminino", 25, "12345678901", "123456789", "12345-678", "Cidade X", "ana@email.com", "Endereço 1", Arrays.asList("Atividades"));

        when(volunteerService.getVolunteerById(1L)).thenReturn(volunteer);

        mockMvc.perform(get("/volunteers/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Ana"));

        verify(volunteerService).getVolunteerById(1L);
    }

    @Test
    void testGetVolunteerById_NotFound() throws Exception {
        when(volunteerService.getVolunteerById(1L)).thenReturn(null);

        mockMvc.perform(get("/volunteers/1"))
            .andExpect(status().isNotFound());

        verify(volunteerService).getVolunteerById(1L);
    }

    // @Test
    // void testUpdateVolunteer_Success() throws Exception {
    //     Volunteer updatedVolunteer =  new Volunteer(
    //         "Ana Caroline",
    //         "Feminino",
    //         25,
    //         "12345678901",
    //         "123456789",
    //         "12345-678",
    //         "Cidade X",
    //         "ana@email.com",
    //         "Endereço 1",
    //         Arrays.asList("Participação em Atividades")
    //     );

    //     when(volunteerService.updateVolunteer(eq(1L), eq(updatedVolunteer))).thenReturn(updatedVolunteer);

    //     mockMvc.perform(put("/volunteers/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(updatedVolunteer)))
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$.name").value("Ana Caroline"));

    //     verify(volunteerService).updateVolunteer(eq(1L), eq(updatedVolunteer));
    // }

    @Test
    void testDeleteVolunteer_Success() throws Exception {
        mockMvc.perform(delete("/volunteers/1"))
            .andExpect(status().isNoContent());

        verify(volunteerService).deleteVolunteer(1L);
    }
}
