package com.sportacademy.project.notifications.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

  @Id
  private String id;
  private String topic;
  private String createDate;
  private boolean checked;
  private String employeeId;
  private String employeeFirstName;
  private String employeeSurName;
  private String description;

}
