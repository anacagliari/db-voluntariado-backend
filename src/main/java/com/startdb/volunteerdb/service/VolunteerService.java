package com.startdb.volunteerdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.repository.VolunteerRepository;

@Service
public class VolunteerService {
  
  @Autowired  // Adiciona a injeção de dependência do repositório
  private VolunteerRepository volunteerRepository;

  public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }
  
}
