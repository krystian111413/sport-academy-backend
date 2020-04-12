package com.sportacademy.project.notifications.application;

import com.sportacademy.project.employees.domain.Employee;
import com.sportacademy.project.employees.infrastructure.EmployeeRepository;
import com.sportacademy.project.employees.application.EmployeeService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sportacademy.project.notifications.infrastructure.NotificationRepository;
import com.sportacademy.project.notifications.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@AllArgsConstructor
@Service
@Slf4j
public class NotificationService {

  private NotificationRepository notificationRepository;
  private EmployeeRepository employeeRepository;
  private EmployeeService employeeService;

  public List<Notification> getLast(Integer count) {
    List<Notification> notifications = notificationRepository.findAll();
    Collections.reverse(notifications);
    return notifications;
  }

  public void update(String id, Notification notification) {
    Notification notificationDB = getNotification(id);
    notificationDB.setChecked(notification.isChecked());
    notificationRepository.save(notificationDB);
  }

  public void create(Notification notification) {
    notificationRepository.save(notification);
  }

  public void createNewNotifications() {
    log.info("Creating notification");
    Long firstAidNotificationDays = 90L;
    Long OHSTestNotificationDays = 30L;
    Long dealNotificationDays = 30L;
    List<Employee> employees = employeeRepository.findAll();
    employees.forEach(employee -> {
      createNotification(
          firstAidNotificationDays,
          employee.getPermissions().getFirstAid().getEndDate(),
          employee,
          "firstAid",
          (employee.getPermissions().getFirstAid().getEndDate() != null ?  employee.getPermissions().getFirstAid().getEndDate().split("T")[0] : "")
              + " dnia kończy się kurs kwalifikowanej pierwszej pomocy");
      createNotification(
          OHSTestNotificationDays,
          employee.getPermissions().getOhstests().getEndDate(),
          employee,
          "ohsTest",
          (employee.getPermissions().getOhstests().getEndDate() != null ? employee.getPermissions().getOhstests().getEndDate().split("T")[0] : "")
              + " dnia upływa termin szkolenia BHP");
      createNotification(
          dealNotificationDays,
          employee.getDeal().getEndDate(),
          employee,
          "deal",
          (employee.getDeal().getEndDate() != null ? employee.getDeal().getEndDate().split("T")[0] : "")
              + " dnia kończy się umowa");
    });
  }

  private void createNotification(Long daysReminder, String endDate, Employee employee, String topic, String desc) {
    if (endDate == null || endDate.equals("")) {
      return;
    }
    Calendar endDateCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      endDateCalendar.setTime(sdf.parse(endDate));
      Calendar now = Calendar.getInstance();
      Long diffDays = endDateCalendar.getTimeInMillis() - now.getTimeInMillis();
      Long days = TimeUnit.DAYS.convert(diffDays, TimeUnit.MILLISECONDS);
      if (days < daysReminder && days > 0) {
        List<Notification> findResult = notificationRepository.findByEmployeeIdAndTopic(employee.getId(), topic);
        if (findResult.isEmpty()) {
          Notification notification = new Notification();
          notification.setChecked(false);
          notification.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(now.getTime()));
          notification.setDescription(desc);
          notification.setEmployeeFirstName(employee.getFirstName());
          notification.setEmployeeSurName(employee.getSurName());
          notification.setEmployeeId(employee.getId());
          notification.setTopic(topic);

          notificationRepository.save(notification);
          log.info("Notification for {} {} created, topic: {}", notification.getEmployeeFirstName(), notification.getEmployeeSurName(), topic);
        }
      }
    } catch (ParseException e) {
      log.error("Can not parse date {}", e);
    }
  }


  private Notification getNotification(String id) {
    return notificationRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
  }

  public void deleteNotifications() {
    log.info("Deleting old notifications");
    Long firsAidDays = 90L;
    Long ohsTestDays = 30L;
    Long dealDays = 30L;
    List<Notification> notifications = notificationRepository.findAll();
    notifications.forEach(notification -> {
      Employee employee = employeeService.getEmployee(notification.getEmployeeId());
      switch (notification.getTopic()) {
        case "firstAid":
          deleteNotification(
              employee.getPermissions().getFirstAid().getEndDate(),
              notification,
              firsAidDays
          );
          break;
        case "ohsTest":
          deleteNotification(
              employee.getPermissions().getOhstests().getEndDate(),
              notification,
              ohsTestDays
          );
          break;
        case "deal":
          deleteNotification(
              employee.getDeal().getEndDate(),
              notification,
              dealDays
          );
      }
    });
  }

  private void deleteNotification(String endDate, Notification notification, Long daysReminder) {
    if (endDate == null || endDate.equals("")) {
      notificationRepository.deleteById(notification.getId());
      return;
    }
    Calendar endDateCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      endDateCalendar.setTime(sdf.parse(endDate));
      Calendar now = Calendar.getInstance();
      Long diffDays = endDateCalendar.getTimeInMillis() - now.getTimeInMillis();
      Long days = TimeUnit.DAYS.convert(diffDays, TimeUnit.MILLISECONDS);
      if (days > daysReminder || days < -40) {
        log.info("Notification removed: {}", notification.toString());
        notificationRepository.deleteById(notification.getId());
      }
    } catch (ParseException e) {
      log.error("Can not parse date {}", e);
    }
  }

  public void removeAllNotifiactionsByEmployeeId(String employeeId) {
    notificationRepository.deleteAllByEmployeeId(employeeId);
  }
}
