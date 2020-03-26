package com.sportacademy.project.notifications;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

  List<Notification> findByEmployeeIdAndTopic(String employeeId, String topic);
}
