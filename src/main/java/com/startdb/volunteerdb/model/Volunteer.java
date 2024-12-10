package com.startdb.volunteerdb.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Volunteer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String phone;
  private String email;
  private String address;
  private List<String> areasDeSuporte;


 public Volunteer() {
  }

  public Volunteer(String name, String phone, String email, String address, List<String> areasDeSuporte) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.address = address;
    this.areasDeSuporte = areasDeSuporte;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<String> getAreasDeSuporte() {
    return areasDeSuporte;
}

public void setAreasDeSuporte(List<String> areasDeSuporte) {
    this.areasDeSuporte = areasDeSuporte;
}


}
