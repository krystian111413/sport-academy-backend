package com.sportacademy.project.employees.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adress {
  private String street;
  private String city;
  private String buildingNumber;
  private String code;
  private String email;
  private String phone;
}
