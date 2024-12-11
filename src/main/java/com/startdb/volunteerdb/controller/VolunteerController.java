package com.startdb.volunteerdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        if (volunteers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Volunteer volunteer = volunteerService.getVolunteerById(id);

        if (volunteer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(volunteer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer( @PathVariable Long id, @RequestBody Volunteer updatedVolunteer) {

    if (updatedVolunteer.getName() == null || updatedVolunteer.getName().isEmpty() ||
        updatedVolunteer.getEmail() == null || updatedVolunteer.getEmail().isEmpty() ||
        updatedVolunteer.getPhone() == null || updatedVolunteer.getPhone().isEmpty() ||
        updatedVolunteer.getAddress() == null || updatedVolunteer.getAddress().isEmpty() ||
        updatedVolunteer.getAreasDeSuporte() == null || updatedVolunteer.getAreasDeSuporte().isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Volunteer volunteer = volunteerService.updateVolunteer(id, updatedVolunteer);

    if (volunteer == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(volunteer, HttpStatus.OK);
}
}
