package com.sportacademy.project.employees;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permissions {
  private Lifeguard lifeguard;
  private RefreshAndEndDate firstAid;
  private List<String> usefulPermissions;
  private String anotherPermission;
  private RefreshAndEndDate medicalExamination;
  private RefreshAndEndDate ohstests;
  private EndDate sanel;
  private EndDate studentCard;
}
