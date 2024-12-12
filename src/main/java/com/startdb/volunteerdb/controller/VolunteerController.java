package com.startdb.volunteerdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        try {
            Volunteer createdVolunteer = volunteerService.createVolunteer(volunteer);
            return new ResponseEntity<>(createdVolunteer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        return volunteers.isEmpty() ? 
            new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
            new ResponseEntity<>(volunteers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable("id") Long id) {
        Volunteer volunteer = volunteerService.getVolunteerById(id);
        return volunteer == null ? 
            new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
            new ResponseEntity<>(volunteer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer( @PathVariable Long id, @RequestBody Volunteer updatedVolunteer) {
        try {
            Volunteer volunteer = volunteerService.updateVolunteer(id, updatedVolunteer);
            return new ResponseEntity<>(volunteer, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
