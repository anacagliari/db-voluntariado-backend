package com.startdb.volunteerdb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startdb.volunteerdb.Enum.GenderEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Volunteer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "O nome é obrigatório")
  private String name;

  @Enumerated(EnumType.STRING)
  @NotNull(message = "O gênero é obrigatório")
  private GenderEnum gender;

  @NotNull(message = "A idade é obrigatória")
  private Integer age;

  @NotNull(message = "O cpf é obrigatório")
  private String cpf;

  @NotNull(message = "O telefone é obrigatório")
  private String phone;

  @NotNull(message = "O e-mail é obrigatório")
  private String email;

  @NotNull(message = "O cep é obrigatório")
  private String cep;

  @NotNull(message = "O logradouro é obrigatório")
  private String address;

  @NotNull(message = "A cidde é obrigatório")
  private String city;

  @JsonIgnore
  @OneToMany(mappedBy = "volunteer")
  private List<Support> supports;

  public Volunteer() {
  }

  public Volunteer(String name, GenderEnum gender, Integer age, String cpf, String phone, String cep, String city,
      String email, String address) {
    this.name = name;
    this.gender = gender;
    this.age = age;
    this.cpf = cpf;
    this.phone = phone;
    this.email = email;
    this.cep = cep;
    this.city = city;
    this.address = address;
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

  public GenderEnum getGender() {
    return gender;
  }

  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
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

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<Support> getSupports() {
    return supports;
  }

  public void setSupports(List<Support> supports) {
    this.supports = supports;
  }

}
