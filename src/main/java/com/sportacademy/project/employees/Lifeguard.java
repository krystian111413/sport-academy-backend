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
public class Lifeguard {

  private String refreshedDate;
  private String endDate;
  private Binary image;
}
