package com.sportacademy.project.notifications.infrastructure;

import java.util.List;

import com.sportacademy.project.notifications.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

  List<Notification> findByEmployeeIdAndTopic(String employeeId, String topic);

  void deleteAllByEmployeeIdAndTopic(String employeeId, String topic);

  void deleteAllByEmployeeId(String employeeId);
}
