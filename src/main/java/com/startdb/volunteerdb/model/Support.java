package com.startdb.volunteerdb.model;

import java.time.LocalDate;

import com.startdb.volunteerdb.Enum.SupportAreaEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Volunteer volunteer;

    @ManyToOne
    private Beneficiary beneficiary;

    @Column
    @Enumerated(EnumType.STRING)
    private SupportAreaEnum supportArea;

    @Column
    private LocalDate dateFrom;

    @Column
    private LocalDate dateTo;

    public Support() {
    }

    public Support(Volunteer volunteer, Beneficiary beneficiary, SupportAreaEnum supportArea, LocalDate dateFrom,
            LocalDate dateTo) {
        this.volunteer = volunteer;
        this.beneficiary = beneficiary;
        this.supportArea = supportArea;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public SupportAreaEnum getSupportArea() {
        return supportArea;
    }

    public void setSupportArea(SupportAreaEnum supportArea) {
        this.supportArea = supportArea;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    

}
