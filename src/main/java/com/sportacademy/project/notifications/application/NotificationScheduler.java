package com.sportacademy.project.notifications.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@EnableScheduling
@Service
public class NotificationScheduler {

  private NotificationService notificationService;

  @Scheduled(initialDelay = 1000, fixedDelay = 21600000)
  public void createNewNotifications() {
    notificationService.createNewNotifications();
  }

  @Scheduled(initialDelay = 1000, fixedDelay = 21600000)
  public void deleteNewNotifications() {
    notificationService.deleteNotifications();
  }

}
