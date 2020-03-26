package com.sportacademy.project.notifications;

import com.sportacademy.project.auth.AuthService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class NotificationsController {

  private AuthService authService;
  private NotificationService notificationService;

  @GetMapping
  @ResponseBody
  public List<Notification> getAll(@RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      return notificationService.getLast(30);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  @PutMapping("/{id}")
  @ResponseBody
  public void update(@RequestHeader("Authorization") String auth,
      @RequestBody Notification notification,
      @PathVariable String id) {
    if (authService.isAuthorization(auth)) {
      notificationService.update(id, notification);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }
}
