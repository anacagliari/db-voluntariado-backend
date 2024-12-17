package com.startdb.volunteerdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startdb.volunteerdb.model.Support;
import com.startdb.volunteerdb.service.SupportService;

@RestController
@RequestMapping("/volunteers/support")
public class SupportController {

    @Autowired
    private SupportService supportService;

    @PostMapping
    public ResponseEntity<Support> createSupport(@RequestBody Support support) {
        try {
            Support createdSupport = supportService.createSupport(support);
            return new ResponseEntity<>(createdSupport, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Support>> getAllSupports() {
        List<Support> supports = supportService.getAllSupports();
        return supports.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(supports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Support> getSupportById(@PathVariable("id") Long id) {
        Support support = supportService.getSupportById(id);
        return support == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(support, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupport(@PathVariable Long id) {
        supportService.deleteSupport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}