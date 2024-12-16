package com.startdb.volunteerdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.startdb.volunteerdb.Enum.GenderEnum;
import com.startdb.volunteerdb.Enum.SupportAreaEnum;
import com.startdb.volunteerdb.model.Beneficiary;
import com.startdb.volunteerdb.repository.BeneficiaryRepository;

public class BeneficiaryServiceTest {

  @Mock
  private BeneficiaryRepository beneficiaryRepository;

  @InjectMocks
  private BeneficiaryService beneficiaryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // testes de sucesso

  @Test
  void testCreateBeneficiary() {

    LocalDate dateFrom = LocalDate.of(2024, 12, 1);
    LocalDate dateTo = LocalDate.of(2024, 12, 31);

    Beneficiary beneficiary = new Beneficiary(
        "Suene Souza",
        GenderEnum.FEMININO,
        60,
        "12345678901",
        "123456789",
        "12345678",
        "Cidade Y",
        "souza@email.com",
        "Endereço A",
        dateFrom,
        dateTo,
        Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription()));
    when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);

    Beneficiary result = beneficiaryService.createBeneficiary(beneficiary);

    assertNotNull(result);
    assertEquals("Suene Souza", result.getName());
    verify(beneficiaryRepository, times(1)).save(beneficiary);
  }

  @Test
  void testGetAllBeneficiaries() {

    LocalDate dateFrom = LocalDate.of(2024, 12, 1);
    LocalDate dateTo = LocalDate.of(2024, 12, 31);

    List<Beneficiary> beneficiaries = Arrays.asList(
        new Beneficiary(
            "Suene Souza",
            GenderEnum.FEMININO,
            60,
            "12345678901",
            "123456789",
            "12345678",
            "Cidade Y",
            "souza@email.com",
            "Endereço A",
            dateFrom,
            dateTo,
            Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription())),
        new Beneficiary(
            "Ana Cagliari",
            GenderEnum.FEMININO,
            60,
            "12345678902",
            "123456789",
            "12345678",
            "Cidade Y",
            "cagliari@email.com",
            "Endereço B",
            dateFrom,
            dateTo,
            Arrays.asList(SupportAreaEnum.REALIZACAO_TAREFAS.getDescription(), SupportAreaEnum.APRENDIZADO_TECNOLOGIA.getDescription())));

    when(beneficiaryRepository.findAll()).thenReturn(beneficiaries);

    List<Beneficiary> result = beneficiaryService.getAllBeneficiaries();

    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
    verify(beneficiaryRepository, times(1)).findAll();
  }

  @Test
  void testGetBeneficiaryById() {

    LocalDate dateFrom = LocalDate.of(2024, 12, 1);
    LocalDate dateTo = LocalDate.of(2024, 12, 31);

    Beneficiary beneficiary = new Beneficiary(
        "Suene Souza",
        GenderEnum.FEMININO,
        60,
        "12345678901",
        "123456789",
        "12345678",
        "Cidade Y",
        "souza@email.com",
        "Endereço A",
        dateFrom,
        dateTo,
        Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription()));
    when(beneficiaryRepository.findById(1L)).thenReturn(Optional.of(beneficiary));

    Beneficiary result = beneficiaryService.getBeneficiaryById(1L);

    assertNotNull(result);
    assertEquals("Suene Souza", result.getName());
    verify(beneficiaryRepository, times(1)).findById(1L);
  }

  @Test
  void testUpdateBeneficiary() {

    LocalDate dateFrom = LocalDate.of(2024, 12, 1);
    LocalDate dateTo = LocalDate.of(2024, 12, 31);

    Beneficiary existingBeneficiary = new Beneficiary(
        "Suene Souza",
        GenderEnum.FEMININO,
        60,
        "12345678901",
        "123456789",
        "12345678",
        "Cidade Y",
        "souza@email.com",
        "Endereço A",
        dateFrom,
        dateTo,
        Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription()));
    Beneficiary updatedBeneficiary = new Beneficiary(
        "Su Souza",
        GenderEnum.FEMININO,
        60,
        "12345678901",
        "123456789",
        "12345678",
        "Cidade Y",
        "souza@email.com",
        "Endereço A",
        dateFrom,
        dateTo,
        Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription()));
    when(beneficiaryRepository.findById(1L)).thenReturn(Optional.of(existingBeneficiary));
    when(beneficiaryRepository.save(existingBeneficiary)).thenReturn(updatedBeneficiary);

    Beneficiary result = beneficiaryService.updateBeneficiary(1L, updatedBeneficiary);

    assertNotNull(result);
    assertEquals("Su Souza", result.getName());
    verify(beneficiaryRepository, times(1)).findById(1L);
    verify(beneficiaryRepository, times(1)).save(existingBeneficiary);
  }

  @Test
  void testDeleteBeneficiary() {
    when(beneficiaryRepository.existsById(1L)).thenReturn(true);

    beneficiaryService.deleteBeneficiary(1L);

    verify(beneficiaryRepository, times(1)).deleteById(1L);
  }

  // testes de atributos vazios

  @Test
  void testCriaBeneficiarioComNomeVazio() {
    Beneficiary beneficiary = new Beneficiary();
    beneficiary.setName("");
    beneficiary.setGender(GenderEnum.FEMININO);
    beneficiary.setAge(60);
    beneficiary.setCpf("12345678901");
    beneficiary.setPhone("123456789");
    beneficiary.setCep("12345678");
    beneficiary.setCity("Cidade Y");
    beneficiary.setEmail("cagliari@email.com");
    beneficiary.setAddress("Endereço B");
    beneficiary.setDateFrom(LocalDate.of(2024, 12, 1));
    beneficiary.setDateTo(LocalDate.of(2024, 12, 31));
    beneficiary.setSupportArea(Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription()));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      beneficiaryService.createBeneficiary(beneficiary);
    });

    assertEquals("Nome do beneficiário é obrigatório.", exception.getMessage());
  }

  @Test
  void testAtualizaBeneficiarioComNomeVazio() {

    LocalDate dateFrom = LocalDate.of(2024, 12, 1);
    LocalDate dateTo = LocalDate.of(2024, 12, 31);

    Beneficiary beneficiaryExistente = new Beneficiary(
        "Suene Souza",
        GenderEnum.FEMININO,
        60,
        "12345678901",
        "123456789",
        "12345678",
        "Cidade Y",
        "souza@email.com",
        "Endereço A",
        dateFrom,
        dateTo,
        Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription()));
    Beneficiary beneficiaryAtualizado = new Beneficiary(
        "",
        GenderEnum.FEMININO,
        60,
        "12345678901",
        "123456789",
        "12345678",
        "Cidade Y",
        "souza@email.com",
        "Endereço A",
        dateFrom,
        dateTo,
        Arrays.asList(SupportAreaEnum.PARTICIPACAO_ATIVIDADES.getDescription()));

    when(beneficiaryRepository.findById(1L)).thenReturn(Optional.of(beneficiaryExistente));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      beneficiaryService.updateBeneficiary(1L, beneficiaryAtualizado);
    });

    assertEquals("Nome do beneficiário é obrigatório.", exception.getMessage());
  }

  // testes id inexistente
  @Test
  void testBuscaBeneficiarioPorIdInexistente() {
    when(beneficiaryRepository.findById(1L)).thenReturn(Optional.empty());

    Beneficiary result = beneficiaryService.getBeneficiaryById(1L);

    assertNull(result);
  }

  @Test
void testDeletaBeneficiarioComIdInexistente() {
    when(beneficiaryRepository.existsById(1L)).thenReturn(false);  

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        beneficiaryService.deleteBeneficiary(1L);
    });

    assertEquals("Beneficiário não encontrado.", exception.getMessage());
  }
}
