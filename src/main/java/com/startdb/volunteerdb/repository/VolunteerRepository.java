package com.startdb.volunteerdb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.startdb.volunteerdb.model.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

  @Query("SELECT COUNT(A) FROM Volunteer A WHERE A.cpf = :cpf")
    Long quantidadeVolunteersCpf(String cpf);
  
}
