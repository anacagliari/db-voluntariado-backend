package com.startdb.volunteerdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.startdb.volunteerdb.Enum.GenderEnum;
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

  // testes de sucesso

  @Test
  void testCreateVolunteer() {
    Volunteer volunteer = new Volunteer(
        "Suene Souza",
        GenderEnum.FEMININO,
        30,
        "12345678901",
        "123456789",
        "12345-678",
        "Cidade X",
        "suene@email.com",
        "Endereço 1");
    when(volunteerRepository.save(volunteer)).thenReturn(volunteer);

    Volunteer result = volunteerService.createVolunteer(volunteer);

    assertNotNull(result);
    assertEquals("Suene Souza", result.getName());
    verify(volunteerRepository, times(1)).save(volunteer);
  }

  @Test
  void testGetAllVolunteers() {
    List<Volunteer> volunteers = Arrays.asList(
        new Volunteer(
            "Suene Souza",
            GenderEnum.FEMININO,
            30,
            "12345678901",
            "123456789",
            "12345-678",
            "Cidade X",
            "suene@email.com",
            "Endereço 1"),
        new Volunteer(
            "Ana Carolina",
            GenderEnum.FEMININO,
            30,
            "12345678902",
            "123456789",
            "12345-678",
            "Cidade X",
            "ana@email.com",
            "Endereço 2"));

    when(volunteerRepository.findAll()).thenReturn(volunteers);

    List<Volunteer> result = volunteerService.getAllVolunteers();

    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
    verify(volunteerRepository, times(1)).findAll();
  }

  @Test
  void testGetVolunteerById() {
    Volunteer volunteer = new Volunteer(
        "Suene Souza",
        GenderEnum.FEMININO,
        30,
        "12345678901",
        "123456789",
        "12345-678",
        "Cidade X",
        "suene@email.com",
        "Endereço 1");
    when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));

    Volunteer result = volunteerService.getVolunteerById(1L);

    assertNotNull(result);
    assertEquals("Suene Souza", result.getName());
    verify(volunteerRepository, times(1)).findById(1L);
  }

  @Test
  void testUpdateVolunteer() {
    Volunteer existingVolunteer = new Volunteer(
        "Suene Souza",
        GenderEnum.FEMININO,
        30,
        "12345678901",
        "123456789",
        "12345-678",
        "Cidade X",
        "suene@email.com",
        "Endereço 1");
    Volunteer updatedVolunteer = new Volunteer(
        "Su Souza",
        GenderEnum.FEMININO,
        30,
        "12345678901",
        "123456789",
        "12345-678",
        "Cidade X",
        "suene@email.com",
        "Endereço 1");
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

    volunteerService.deleteVolunteer(1L);

    verify(volunteerRepository, times(1)).deleteById(1L);
  }

  // testes de atributos vazios

  @Test
  void testCriaVoluntarioComNomeVazio() {
    Volunteer volunteer = new Volunteer();
    volunteer.setName("");
    volunteer.setGender(GenderEnum.FEMININO);
    volunteer.setAge(30);
    volunteer.setCpf("12345678901");
    volunteer.setPhone("123456789");
    volunteer.setCep("12345-678");
    volunteer.setCity("Cidade X");
    volunteer.setEmail("suene@email.com");
    volunteer.setAddress("Endereço 1");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      volunteerService.createVolunteer(volunteer);
    });

    assertEquals("Nome do voluntário é obrigatório.", exception.getMessage());
  }

  @Test
  void testAtualizaVoluntarioComNomeVazio() {
    Volunteer volunteerExistente = new Volunteer(
        "Suene Souza",
        GenderEnum.FEMININO,
        30,
        "12345678901",
        "123456789",
        "12345-678",
        "Cidade X",
        "suene@email.com",
        "Endereço 1");
    Volunteer volunteerAtualizado = new Volunteer(
        "",
        GenderEnum.FEMININO,
        30,
        "12345678901",
        "123456789",
        "12345-678",
        "Cidade X",
        "suene@email.com",
        "Endereço 1");

    when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteerExistente));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      volunteerService.updateVolunteer(1L, volunteerAtualizado);
    });

    assertEquals("Nome do voluntário é obrigatório.", exception.getMessage());
  }

  // testes id inexistente
  @Test
  void testBuscaVoluntarioPorIdInexistente() {
    when(volunteerRepository.findById(1L)).thenReturn(Optional.empty());

    Volunteer result = volunteerService.getVolunteerById(1L);

    assertNull(result);
  }

  @Test
void testDeletaVoluntarioComIdInexistente() {
    when(volunteerRepository.existsById(1L)).thenReturn(false);  

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        volunteerService.deleteVolunteer(1L);
    });

    assertEquals("Voluntário não encontrado.", exception.getMessage());
  }
}
