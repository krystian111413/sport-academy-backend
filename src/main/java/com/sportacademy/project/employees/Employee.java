package com.sportacademy.project.employees;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
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
  private String lastName;
  private String pesel;
  private String cityCode;
  private String street;
  private String buildingNumber;
  private String taxOfficeAddress;
  private List<String> permissions;
}
