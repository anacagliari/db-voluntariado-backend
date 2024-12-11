package com.startdb.volunteerdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.repository.VolunteerRepository;

@Service
public class VolunteerService {
  
  @Autowired  
  private VolunteerRepository volunteerRepository;

  public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

  public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

  public Volunteer getVolunteerById(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }
  
}
