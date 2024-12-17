package com.startdb.volunteerdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startdb.volunteerdb.model.Beneficiary;
import com.startdb.volunteerdb.model.Support;
import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.repository.SupportRepository;

@Service
public class SupportService {

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private BeneficiaryService beneficiaryService;

    public Support createSupport(Support support) {
        if (support == null) {
            throw new IllegalArgumentException("Dados não informados.");
        }
        if (support.getVolunteer() == null || support.getVolunteer().getId() == null) {
            throw new IllegalArgumentException("Voluntário não informado.");
        }
        if (support.getBeneficiary() == null || support.getBeneficiary().getId() == null) {
            throw new IllegalArgumentException("Beneficiário não informado.");
        }
        if (support.getSupportArea() == null) {
            throw new IllegalArgumentException("Área de atuação não informada.");
        }
        if (support.getDateFrom() == null) {
            throw new IllegalArgumentException("Data de início não informada.");
        }
        if (support.getDateTo() == null) {
            throw new IllegalArgumentException("Data de término não informada.");
        }
        if (support.getDateFrom().isAfter(support.getDateTo())) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de término.");
        }

        Volunteer volunteer = volunteerService.getVolunteerById(support.getVolunteer().getId());

        if (volunteer == null) {
            throw new IllegalArgumentException("Voluntário não encontrado.");
        }

        Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(support.getBeneficiary().getId());

        if (beneficiary == null) {
            throw new IllegalArgumentException("Beneficiário não encontrado.");
        }

        support.setVolunteer(volunteer);
        support.setBeneficiary(beneficiary);

        return supportRepository.save(support);
    }

    public List<Support> getAllSupports() {
        return supportRepository.findAll();
    }

    public Support getSupportById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não informado.");
        }
        return supportRepository.findById(id).orElse(null);
    }

    public void deleteSupport(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não informado.");
        }
        if (!supportRepository.existsById(id)) {
            throw new IllegalArgumentException("Beneficiário não encontrado.");
        }

        supportRepository.deleteById(id);
    }

}
