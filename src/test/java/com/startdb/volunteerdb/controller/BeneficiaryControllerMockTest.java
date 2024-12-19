package com.startdb.volunteerdb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import com.startdb.volunteerdb.Enum.GenderEnum;
import com.startdb.volunteerdb.Enum.SupportAreaEnum;
import com.startdb.volunteerdb.model.Beneficiary;
import com.startdb.volunteerdb.service.BeneficiaryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class BeneficiaryControllerMockTest {

@Mock
    private BeneficiaryService beneficiaryService;

    @InjectMocks
    private BeneficiaryController beneficiaryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(beneficiaryController).build();
    }

    @Test
    void testCreateBeneficiary_Success() throws Exception {
        Beneficiary beneficiary = new Beneficiary(
                "Ana Cagliari", GenderEnum.FEMININO, 30, "12345678900", "51912345678", "90123450",
                "Porto Alegre", "ana.cagliari@example.com", "Rua A 123",
                LocalDate.now(), LocalDate.now(),
                SupportAreaEnum.APRENDIZADO_TECNOLOGIA); 
    
        when(beneficiaryService.createBeneficiary(any(Beneficiary.class))).thenReturn(beneficiary);
    
        mockMvc.perform(post("/beneficiaries")
                .contentType("application/json")
                .content("{ \"name\": \"Ana Cagliari\", \"gender\": \"FEMININO\", \"age\": 30, \"cpf\": \"12345678900\", " +
                        "\"phone\": \"51912345678\", \"email\": \"ana.cagliari@example.com\", \"cep\": \"90123450\", " +
                        "\"address\": \"Rua A 123\", \"city\": \"Porto Alegre\", \"dateFrom\": \"2024-12-17\", " +
                        "\"dateTo\": \"2025-12-17\", \"supportArea\": \"APRENDIZADO_TECNOLOGIA\" }")) 
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ana Cagliari"));
    }
    

    @Test
    void testDeleteBeneficiary_Success() throws Exception {
        Long beneficiaryId = 1L;
    
        mockMvc.perform(delete("/beneficiaries/{id}", beneficiaryId))
                .andExpect(status().isNoContent());
    
        verify(beneficiaryService, times(1)).deleteBeneficiary(beneficiaryId);
    }
    
    @Test
    void testGetBeneficiaryById_NotFound() throws Exception {
        Long beneficiaryId = 1L;
        when(beneficiaryService.getBeneficiaryById(beneficiaryId)).thenReturn(null);
    
        mockMvc.perform(get("/beneficiaries/{id}", beneficiaryId))
                .andExpect(status().isNotFound());
    }
}