/*package com.startdb.volunteerdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.repository.VolunteerRepository;

public class VolunteerServiceTest {

  @Mock
  private VolunteerRepository volunteerRepository;

  @InjectMocks
  private VolunteerService volunteerService;

  @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  @Test
  void testCreateVolunteer() {
    Volunteer volunteer = new Volunteer("Suene Souza", "123456789", "suene@email.com", "enderoço 1", Arrays.asList("Participação em Atividades Recreativas"));
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);

        Volunteer result = volunteerService.createVolunteer(volunteer);

        assertNotNull(result);
        assertEquals("Suene Souza", result.getName());
        verify(volunteerRepository, times(1)).save(volunteer);
    }

  @Test
  void testGetAllVolunteers() {
    List<Volunteer> volunteers = Arrays.asList(
                new Volunteer("Suene Souza", "123456789", "suene@email.com", "enderoço 1", Arrays.asList("Participação em Atividades Recreativas"))
        );
        when(volunteerRepository.findAll()).thenReturn(volunteers);

        List<Volunteer> result = volunteerService.getAllVolunteers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(volunteerRepository, times(1)).findAll();

  }

  @Test
  void testGetVolunteerById() {
    Volunteer volunteer = new Volunteer("Suene Souza", "123456789", "suene@email.com", "enderoço 1", Arrays.asList("Participação em Atividades Recreativas"));
    when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));

    Volunteer result = volunteerService.getVolunteerById(1L);

    assertNotNull(result);
    assertEquals("Suene Souza", result.getName());
    verify(volunteerRepository, times(1)).findById(1L);
  }

  

  @Test
  void testUpdateVolunteer() {
    Volunteer existingVolunteer = new Volunteer("Suene Souza", "123456789", "suene@email.com", "enderoço 1", Arrays.asList("Participação em Atividades Recreativas"));
        Volunteer updatedVolunteer = new Volunteer("Su Souza", "123456789", "suene@email.com", "enderoço 1", Arrays.asList("Participação em Atividades Recreativas"));
        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(existingVolunteer));
        when(volunteerRepository.save(existingVolunteer)).thenReturn(updatedVolunteer);

        Volunteer result = volunteerService.updateVolunteer(1L, updatedVolunteer);

        assertNotNull(result);
        assertEquals("Su Souza", result.getName());
        verify(volunteerRepository, times(1)).findById(1L);
        verify(volunteerRepository, times(1)).save(existingVolunteer);
  }

  
  @Test
  void testDeleteVolunteer() {
    when(volunteerRepository.existsById(1L)).thenReturn(true);

        boolean result = volunteerService.deleteVolunteer(1L);

        assertTrue(result);
        verify(volunteerRepository, times(1)).existsById(1L);
        verify(volunteerRepository, times(1)).deleteById(1L);
    }

}*/
