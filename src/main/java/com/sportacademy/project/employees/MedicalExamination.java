package com.sportacademy.project.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalExamination {

  private Binary image;
  private String refreshedDate;
  private String endDate;
}
