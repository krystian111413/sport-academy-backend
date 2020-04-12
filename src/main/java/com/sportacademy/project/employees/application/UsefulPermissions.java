package com.sportacademy.project.employees.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsefulPermissions {

  private Boolean frogman;
  private Boolean swimmingInstructor;
  private Boolean yachtSailor;
  private Boolean helmsman;
  private Binary frogmanImage;
  private Binary swimmingInstructorImage;
  private Binary yachtSailorImage;
  private Binary helmsmanImage;
}
