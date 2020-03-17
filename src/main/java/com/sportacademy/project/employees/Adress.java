package com.sportacademy.project.employees;

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
}
