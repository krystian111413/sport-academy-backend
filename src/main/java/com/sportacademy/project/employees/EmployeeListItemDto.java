package com.sportacademy.project.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListItemDto {
  private String id;
  private String firstName;
  private String surName;
  private String city;
  private String dealEndDate;

}
