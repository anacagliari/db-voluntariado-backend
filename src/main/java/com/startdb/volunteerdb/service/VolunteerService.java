package com.startdb.volunteerdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.startdb.volunteerdb.Enum.GenderEnum;
import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.repository.VolunteerRepository;

@Service
public class VolunteerService {

  @Autowired
  private VolunteerRepository volunteerRepository;

  public Volunteer createVolunteer(Volunteer volunteer) {
    this.validateSaveVolunteer(volunteer, null);
    return volunteerRepository.save(volunteer);
  }

  public List<Volunteer> getAllVolunteers() {
    return volunteerRepository.findAll();
  }

  public Volunteer getVolunteerById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID não informado.");
    }
    return volunteerRepository.findById(id).orElse(null);
  }

  public Volunteer updateVolunteer(Long id, Volunteer volunteer) {
    Optional<Volunteer> volunteerExiste = volunteerRepository.findById(id);
    if (!volunteerExiste.isPresent()) {
      throw new IllegalArgumentException("volunteer não encontrado.");
    }
    Volunteer volunteerExistente = volunteerExiste.get();

    this.validateSaveVolunteer(volunteer, volunteerExistente);
    volunteerExistente.setName(volunteer.getName());
    volunteerExistente.setGender(volunteer.getGender());
    volunteerExistente.setAge(volunteer.getAge());
    volunteerExistente.setCpf(volunteer.getCpf());
    volunteerExistente.setPhone(volunteer.getPhone());
    volunteerExistente.setEmail(volunteer.getEmail());
    volunteerExistente.setCep(volunteer.getCep());
    volunteerExistente.setAddress(volunteer.getAddress());
    volunteerExistente.setCity(volunteer.getCity());
    volunteerExistente.setAreasDeSuporte(volunteer.getAreasDeSuporte());

    return volunteerRepository.save(volunteerExistente);
  }

  public void deleteVolunteer(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID não informado.");
    }
    if (!volunteerRepository.existsById(id)) {
      throw new IllegalArgumentException("Voluntário não encontrado.");
    }

    volunteerRepository.deleteById(id);
  }

  private void validateSaveVolunteer(Volunteer volunteer, Volunteer volunteerExistente) {
    if (volunteer.getName() == null || volunteer.getName().isEmpty()) {
      throw new IllegalArgumentException("Nome do voluntário é obrigatório.");
    }
    if (!volunteer.getGender().equalsIgnoreCase(GenderEnum.FEMININO.getValor())
        && !volunteer.getGender().equalsIgnoreCase(GenderEnum.MASCULINO.getValor())) {
      throw new IllegalArgumentException("Gênero é obrigatório.");
    }
    if (volunteer.getAge() == null) {
      throw new IllegalArgumentException("Idade do voluntário é obrigatório.");
    }
    if (volunteer.getCpf() == null || volunteer.getCpf().isEmpty()) {
      throw new IllegalArgumentException("CPF do voluntário é obrigatório.");
    }
    if (volunteer.getCpf().length() != 11) {
      throw new IllegalArgumentException("CPF deve conter onze dígitos numérico.");
    }
    for (char c : volunteer.getCpf().toCharArray()) {
      if (!Character.isDigit(c)) {
        throw new IllegalArgumentException("CPF deve conter apenas dígitos numérico.");
      }
    }
    if (volunteerExistente == null || !volunteer.getCpf().equals(volunteerExistente.getCpf())) {
      if (volunteerRepository.quantidadeVolunteersCpf(volunteer.getCpf()) > 0) {
        throw new IllegalArgumentException("CPF existente.");
      }
    }
    if (volunteer.getPhone() == null) {
      throw new IllegalArgumentException("O telefone é obrigatório.");
    }
    if (volunteer.getEmail() == null) {
      throw new IllegalArgumentException("O e-mail é obrigatório.");
    }
    if (volunteer.getCep() == null) {
      throw new IllegalArgumentException("O cep é obrigatório.");
    }
    if (volunteer.getAddress() == null) {
      throw new IllegalArgumentException("O logradouro é obrigatório.");
    }
    if (volunteer.getCity() == null) {
      throw new IllegalArgumentException("A cidade é obrigatória.");
    }
    if (volunteer.getAreasDeSuporte() == null || volunteer.getAreasDeSuporte().isEmpty()) {
      throw new IllegalArgumentException("A área de suporte é obrigatória.");
    }
  }
}