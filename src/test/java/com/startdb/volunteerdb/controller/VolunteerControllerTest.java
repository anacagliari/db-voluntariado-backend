/*package com.startdb.volunteerdb.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.service.VolunteerService;

public class VolunteerControllerTest {

  @Autowired
	private MockMvc mvc;

  @Mock
    private VolunteerService volunteerService;

  @InjectMocks
  private VolunteerController volunteerController;

  @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(volunteerController).build();
    }

  @Test
  void testCreateVolunteer() throws Exception {
    

  }

  @Test
  void testGetAllVolunteers() throws Exception {

    List<Volunteer> mockVolunteers = List.of(
            new Volunteer("Suene Souza", "123456789", "suene@email.com", "Endereço 1", Arrays.asList("Participação em Atividades Recreativas")),
            new Volunteer("Ana Carolina", "987654321", "ana@email.com", "Endereço 2", Arrays.asList("Aprendizado de Tecnologia")),
            new Volunteer("Carlos Silva", "987654321", "carlos@email.com", "Endereço 3", Arrays.asList("Realização de Tarefas Cotidianas"))
    );

    when(volunteerService.getAllVolunteers()).thenReturn(mockVolunteers);

    mvc.perform(MockMvcRequestBuilders.get("/volunteers").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(mockVolunteers.size())) 
        .andExpect(jsonPath("$[0].name").value("Suene Souza")) 
        .andExpect(jsonPath("$[0].email").value("suene@email.com")) 
        .andExpect(jsonPath("$[0].areasDeSuporte[0]").value("Participação em Atividades Recreativas")) 
        .andExpect(jsonPath("$[1].name").value("Ana Carolina")) 
        .andExpect(jsonPath("$[1].areasDeSuporte[0]").value("Aprendizado de Tecnologia")) 
        .andExpect(jsonPath("$[1].email").value("ana@email.com")) 
        .andExpect(jsonPath("$[2].name").value("Carlos Silva")); 
  }

  @Test
  void testGetVolunteerById() {

  }

  @Test
  void testUpdateVolunteer() {

  }

  @Test
  void testDeleteVolunteer() {

  }
}*/
