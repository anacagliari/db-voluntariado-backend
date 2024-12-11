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

  public Volunteer updateVolunteer(Long id, Volunteer updatedVolunteer) {
    return volunteerRepository.findById(id).map(volunteer -> {
      volunteer.setName(updatedVolunteer.getName());
      volunteer.setPhone(updatedVolunteer.getPhone());
      volunteer.setEmail(updatedVolunteer.getEmail());
      volunteer.setAddress(updatedVolunteer.getAddress());
      volunteer.setAreasDeSuporte(updatedVolunteer.getAreasDeSuporte());
      return volunteerRepository.save(volunteer);
    }).orElse(null);
  }

}
