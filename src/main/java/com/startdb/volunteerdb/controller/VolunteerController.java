package com.startdb.volunteerdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startdb.volunteerdb.model.Volunteer;
import com.startdb.volunteerdb.service.VolunteerService;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        if (volunteer.getName() == null || volunteer.getName().isEmpty() ||
            volunteer.getEmail() == null || volunteer.getEmail().isEmpty()||
            volunteer.getPhone() == null || volunteer.getPhone().isEmpty()||
            volunteer.getAddress() == null || volunteer.getAddress().isEmpty()||
            volunteer.getAreasDeSuporte() == null || volunteer.getAreasDeSuporte().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

        Volunteer createdVolunteer = volunteerService.createVolunteer(volunteer);

        return new ResponseEntity<>(createdVolunteer, HttpStatus.CREATED); 
    }
  
}
