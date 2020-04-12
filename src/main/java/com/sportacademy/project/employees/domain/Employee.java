package com.sportacademy.project.employees.domain;

import com.sportacademy.project.employees.application.TaxAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employees")
public class Employee {

  @Id
  private String id;
  private String firstName;
  private String surName;
  private String pesel;
  private Adress personalAddress;
  private TaxAddress taxOfficeAddress;
  private Integer yearOfBirthday;
  private Deal deal;
  private Permissions permissions;
}
