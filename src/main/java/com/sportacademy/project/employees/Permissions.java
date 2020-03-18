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
public class Permissions {
  private Lifeguard lifeguard;
  private FirstAid firstAid;
  private UsefulPermissions usefulPermissions;
  private String anotherPermission;
  private Binary anotherPermissionImage;
  private MedicalExamination medicalExamination;
  private Ohstests ohstests;
  private EndDate sanel;
  private EndDate studentCard;
}
