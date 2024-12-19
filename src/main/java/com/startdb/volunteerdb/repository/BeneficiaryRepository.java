package com.startdb.volunteerdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.startdb.volunteerdb.model.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    @Query("SELECT COUNT(A) FROM Beneficiary A WHERE A.cpf = :cpf")
    Long quantidadeBeneficiariesCpf(String cpf);

}
