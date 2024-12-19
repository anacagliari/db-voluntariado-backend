package com.startdb.volunteerdb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
        // Arrange: Criação de um objeto Beneficiary mockado
        Beneficiary beneficiary = new Beneficiary(
                "Ana Cagliari",
                GenderEnum.FEMININO,
                30,
                "12345678900",
                "51912345678",
                "90123450",
                "Porto Alegre",
                "ana.cagliari@example.com",
                "Rua A 123 ",
                LocalDate.now(),
                LocalDate.now(),
                Arrays.asList(SupportAreaEnum.APRENDIZADO_TECNOLOGIA.getDescription()));

        when(beneficiaryService.createBeneficiary(any(Beneficiary.class))).thenReturn(beneficiary);

        // Act: Chamando o endpoint POST para criação de um Beneficiário
        mockMvc.perform(post("/beneficiaries")
                .contentType("application/json")
                .content(
                        "{ \"name\": \"Ana Cagliari\", \"gender\": \"FEMININO\", \"age\": 30, \"cpf\": \"12345678900\", "
                                +
                                "\"phone\": \"51912345678\", \"email\": \"ana.cagliari@example.com\", \"cep\": \"90123450\", "
                                +
                                "\"address\": \"Rua A 123\", \"city\": \"Porto Alegre\", \"dateFrom\": \"2024-12-17\", "
                                +
                                "\"dateTo\": \"2025-12-17\", \"supportArea\": [\"APRENDIZADO_TECNOLOGIA\"] }"))
                .andExpect(status().isCreated()) // Verifica se o status HTTP é 201 (Created)
                .andExpect(jsonPath("$.name").value("Ana Cagliari"))
                .andExpect(jsonPath("$.gender").value("FEMININO"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.cpf").value("12345678900"));

        // Verificar se o método do serviço foi chamado
        verify(beneficiaryService, times(1)).createBeneficiary(any(Beneficiary.class));
    }

    @Test
    void testCreateBeneficiary_BadRequest() throws Exception {
        // Arrange: Simula erro no serviço
        when(beneficiaryService.createBeneficiary(any(Beneficiary.class))).thenThrow(new IllegalArgumentException());

        // Act: Chamando o endpoint com dados inválidos
        mockMvc.perform(post("/beneficiaries")
                .contentType("application/json")
                .content("{ \"name\": \"\", \"gender\": \"FEMININO\", \"age\": 30, \"cpf\": \"12345678900\", " +
                        "\"phone\": \"51912345678\", \"email\": \"ana.cagliari@example.com\", \"cep\": \"90123450\", " +
                        "\"address\": \"Rua A 123\", \"city\": \"Porto Alegre\", \"dateFrom\": \"2024-12-17\", " +
                        "\"dateTo\": \"2025-12-17\", \"supportArea\": [\"APRENDIZADO_TECNOLOGIA\"] }"))
                .andExpect(status().isBadRequest()); // Verifica se o status HTTP é 400 (Bad Request)
    }

    // Teste quando a lista de beneficiários está vazia
    @Test
    void testGetAllBeneficiaries_EmptyList() throws Exception {
        // Arrange: Simula o serviço retornando uma lista vazia
        when(beneficiaryService.getAllBeneficiaries()).thenReturn(List.of());

        // Act: Chamando o endpoint GET para recuperar todos os beneficiários
        mockMvc.perform(get("/beneficiaries"))
                .andExpect(status().isNotFound()); // Verifica se o status HTTP é 404 (NOT_FOUND)
    }

    // Teste quando há beneficiários na lista
    @Test
    void testGetAllBeneficiaries_Success() throws Exception {
        // Arrange: Simula o serviço retornando uma lista com 2 beneficiários
        Beneficiary beneficiary1 = new Beneficiary("Ana Cagliari", GenderEnum.FEMININO, 30, "12345678900",
                "51912345678",
                "90123450", "Porto Alegre", "ana.cagliari@example.com",
                "Rua A 123", LocalDate.now(), LocalDate.now(),
                Arrays.asList(SupportAreaEnum.APRENDIZADO_TECNOLOGIA.getDescription()));

        Beneficiary beneficiary2 = new Beneficiary("Carlos Silva", GenderEnum.MASCULINO, 35, "98765432100",
                "51987654321",
                "98765432", "Porto Alegre", "carlos.silva@example.com",
                "Rua B 456", LocalDate.now(), LocalDate.now(),
                Arrays.asList(SupportAreaEnum.APRENDIZADO_TECNOLOGIA.getDescription()));

        List<Beneficiary> beneficiaries = Arrays.asList(beneficiary1, beneficiary2);

        when(beneficiaryService.getAllBeneficiaries()).thenReturn(beneficiaries);

        // Act: Chamando o endpoint GET para recuperar todos os beneficiários
        mockMvc.perform(get("/beneficiaries"))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 (OK)
                .andExpect(jsonPath("$[0].name").value("Ana Cagliari"))
                .andExpect(jsonPath("$[1].name").value("Carlos Silva"));
    }

    // Teste quando o beneficiário é encontrado
    @Test
    void testGetBeneficiaryById_Success() throws Exception {
        // Arrange: Simula o serviço retornando um beneficiário com ID 1
        Long beneficiaryId = 1L;
        Beneficiary beneficiary = new Beneficiary(
                "Ana Cagliari",
                GenderEnum.FEMININO,
                30,
                "12345678900",
                "51912345678",
                "90123450",
                "Porto Alegre",
                "ana.cagliari@example.com",
                "Rua A 123 ",
                LocalDate.now(),
                LocalDate.now(),
                Arrays.asList(SupportAreaEnum.APRENDIZADO_TECNOLOGIA.getDescription()));

        when(beneficiaryService.getBeneficiaryById(beneficiaryId)).thenReturn(beneficiary);

        // Act: Chamando o endpoint GET para recuperar o beneficiário pelo ID
        mockMvc.perform(get("/beneficiaries/{id}", beneficiaryId))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 (OK)
                .andExpect(jsonPath("$.name").value("Ana Cagliari"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.cpf").value("12345678900"));
    }

    // Teste quando o beneficiário não é encontrado
    @Test
    void testGetBeneficiaryById_NotFound() throws Exception {
        // Arrange: Simula o serviço retornando null (não encontrou o beneficiário)
        Long beneficiaryId = 1L;
        when(beneficiaryService.getBeneficiaryById(beneficiaryId)).thenReturn(null);

        // Act: Chamando o endpoint GET para recuperar o beneficiário pelo ID
        mockMvc.perform(get("/beneficiaries/{id}", beneficiaryId))
                .andExpect(status().isNotFound()); // Verifica se o status HTTP é 404 (NOT_FOUND)
    }

    // @Test
    // void testUpdateBeneficiary_Success() throws Exception {
    //     // Arrange: Beneficiário existente e dados válidos para atualização
    //     Beneficiary existingBeneficiary = new Beneficiary(
    //         "Ana Cagliari",
    //         GenderEnum.FEMININO,
    //         30,
    //         "12345678900",
    //         "51912345679",
    //         "90123451", 
    //         "Porto Alegre", 
    //         "ana.cagliari@example.com",
    //         "Rua A 123", 
    //         LocalDate.now(), 
    //         LocalDate.now(),
    //         Arrays.asList(SupportAreaEnum.APRENDIZADO_TECNOLOGIA.getDescription())
    //     );
    //     Beneficiary updatedBeneficiary = new Beneficiary(
    //         "Ana Cagliari Cappellari",
    //         GenderEnum.FEMININO,
    //         30,
    //         "12345678900",
    //         "51912345679",
    //         "90123451", 
    //         "Porto Alegre", 
    //         "ana.cagliari@example.com",
    //         "Rua A 123", 
    //         LocalDate.now(), 
    //         LocalDate.now(),
    //         Arrays.asList(SupportAreaEnum.APRENDIZADO_TECNOLOGIA.getDescription())
    //     );
    //     // Configurando o comportamento dos mocks
    //     when(beneficiaryService.getBeneficiaryById(1L)).thenReturn(existingBeneficiary); // Simula que o beneficiário existe
    //     when(beneficiaryService.updateBeneficiary(1L, updatedBeneficiary)).thenReturn(updatedBeneficiary); // Simula a atualização
    //     // Act: Realizando a requisição PUT via MockMvc
    //     mockMvc.perform(put("/beneficiaries/{id}", 1L)
    //             .contentType("application/json")
    //             .content("{ \"name\": \"Ana Cagliari Cappellari\", \"gender\": \"FEMININO\", \"age\": 30, \"cpf\": \"12345678900\", " +
    //                     "\"phone\": \"51912345679\", \"email\": \"ana.cagliari@example.com\", \"cep\": \"90123451\", " +
    //                     "\"address\": \"Rua A 123\", \"city\": \"Porto Alegre\", \"dateFrom\": \"2024-12-17\", " +
    //                     "\"dateTo\": \"2025-12-17\", \"supportArea\": [\"APRENDIZADO_TECNOLOGIA\"] }"))
    //             .andExpect(status().isOk()) // Verifica se o status é 200 (OK)
    //             .andExpect(jsonPath("$.name").value("Ana Cagliari Cappellari")) // Verifica se o nome foi atualizado
    //             .andExpect(jsonPath("$.age").value(30)); // Verifica se a idade é a mesma
    //     // Verifica se os métodos mockados foram chamados
    //     verify(beneficiaryService, times(1)).updateBeneficiary(1L, updatedBeneficiary);
    //     verify(beneficiaryService, times(1)).getBeneficiaryById(1L);
    // }

    @Test
    void testDeleteBeneficiary_Success() throws Exception {
        // Arrange: ID do beneficiário a ser deletado
        Long beneficiaryId = 1L;

        // Act: Chamando o endpoint DELETE para excluir o beneficiário
        mockMvc.perform(delete("/beneficiaries/{id}", beneficiaryId))
                .andExpect(status().isNoContent()); // Verifica se o status HTTP é 204 (No Content)

        // Verificar se o método deleteBeneficiary foi chamado no serviço
        verify(beneficiaryService, times(1)).deleteBeneficiary(beneficiaryId);
    }

}
