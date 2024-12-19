package com.startdb.volunteerdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.startdb.volunteerdb.model.Support;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

}
