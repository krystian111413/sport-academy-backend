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
public class Deal {
  private Binary image;
  private String place;
  private String startDate;
  private String endDate;
}
