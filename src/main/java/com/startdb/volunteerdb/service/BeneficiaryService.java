package com.startdb.volunteerdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.startdb.volunteerdb.Enum.GenderEnum;
import com.startdb.volunteerdb.model.Beneficiary;
import com.startdb.volunteerdb.repository.BeneficiaryRepository;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public Beneficiary createBeneficiary(Beneficiary beneficiary) {
        this.validateSaveBeneficiary(beneficiary, null);
        return beneficiaryRepository.save(beneficiary);
    }

    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryRepository.findAll();
    }

    public Beneficiary getBeneficiaryById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não informado.");
        }
        return beneficiaryRepository.findById(id).orElse(null);
    }

    public Beneficiary updateBeneficiary(Long id, Beneficiary beneficiary) {
        Optional<Beneficiary> beneficiaryExiste = beneficiaryRepository.findById(id);
        if (!beneficiaryExiste.isPresent()) {
            throw new IllegalArgumentException("beneficiary não encontrado.");
        }
        Beneficiary beneficiaryExistente = beneficiaryExiste.get();

        this.validateSaveBeneficiary(beneficiary, beneficiaryExistente);
        beneficiaryExistente.setName(beneficiary.getName());
        beneficiaryExistente.setGender(beneficiary.getGender());
        beneficiaryExistente.setAge(beneficiary.getAge());
        beneficiaryExistente.setCpf(beneficiary.getCpf());
        beneficiaryExistente.setPhone(beneficiary.getPhone());
        beneficiaryExistente.setEmail(beneficiary.getEmail());
        beneficiaryExistente.setCep(beneficiary.getCep());
        beneficiaryExistente.setAddress(beneficiary.getAddress());
        beneficiaryExistente.setCity(beneficiary.getCity());
        beneficiaryExistente.setDateFrom(beneficiary.getDateFrom());
        beneficiaryExistente.setDateTo(beneficiary.getDateTo());
        beneficiaryExistente.setSupportArea(beneficiary.getSupportArea());

        return beneficiaryRepository.save(beneficiaryExistente);
    }

    public void deleteBeneficiary(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não informado.");
        }
        if (!beneficiaryRepository.existsById(id)) {
            throw new IllegalArgumentException("Beneficiário não encontrado.");
        }

        beneficiaryRepository.deleteById(id);
    }

    private void validateSaveBeneficiary(Beneficiary beneficiary, Beneficiary beneficiaryExistente) {
        if (beneficiary.getName() == null || beneficiary.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome do beneficiário é obrigatório.");
        }
        if (!beneficiary.getGender().equalsIgnoreCase(GenderEnum.FEMININO.getValor())
                && !beneficiary.getGender().equalsIgnoreCase(GenderEnum.MASCULINO.getValor())) {
            throw new IllegalArgumentException("Gênero é obrigatório.");
        }
        if (beneficiary.getAge() == null) {
            throw new IllegalArgumentException("Idade do beneficiário é obrigatório.");
        }
        if (beneficiary.getCpf() == null || beneficiary.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF do beneficiário é obrigatório.");
        }
        if (beneficiary.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF deve conter onze dígitos numérico.");
        }
        for (char c : beneficiary.getCpf().toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("CPF deve conter apenas dígitos numérico.");
            }
        }
        if (beneficiaryExistente == null || !beneficiary.getCpf().equals(beneficiaryExistente.getCpf())) {
            if (beneficiaryRepository.quantidadeBeneficiariesCpf(beneficiary.getCpf()) > 0) {
                throw new IllegalArgumentException("CPF existente.");
            }
        }
        if (beneficiary.getPhone() == null) {
            throw new IllegalArgumentException("O telefone é obrigatório.");
        }
        if (beneficiary.getEmail() == null) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (beneficiary.getCep() == null) {
            throw new IllegalArgumentException("O cep é obrigatório.");
        }
        if (beneficiary.getAddress() == null) {
            throw new IllegalArgumentException("O logradouro é obrigatório.");
        }
        if (beneficiary.getCity() == null) {
            throw new IllegalArgumentException("A cidade é obrigatória.");
        }
        if (beneficiary.getDateFrom() == null) {
            throw new IllegalArgumentException("A data de início é obrigatória.");
        }
        if (beneficiary.getDateTo() == null) {
            throw new IllegalArgumentException("A data de término é obrigatória.");
        }
        if (beneficiary.getSupportArea() == null || beneficiary.getSupportArea().isEmpty()) {
            throw new IllegalArgumentException("A área de suporte é obrigatória.");
        }
    }
}
