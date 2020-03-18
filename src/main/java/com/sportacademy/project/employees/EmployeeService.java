package com.sportacademy.project.employees;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import sun.util.calendar.BaseCalendar.Date;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeService {

  private EmployeeRepository employeeRepository;

  public void storeFileForEmployee(MultipartFile file, String id, String fileName) {
    Employee employee = getEmployee(id);
    try {

      switch (fileName) {
        case "deal":
          employee.getDeal().setImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "lifeguard":
          employee.getPermissions().getLifeguard().setImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "firstAid":
          employee.getPermissions().getFirstAid().setImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "frogman":
          employee.getPermissions().getUsefulPermissions().setFrogmanImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "swimmingInstructor":
          employee.getPermissions().getUsefulPermissions().setSwimmingInstructorImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "yachtSailor":
          employee.getPermissions().getUsefulPermissions().setYachtSailorImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "anotherPermission":
          employee.getPermissions().setAnotherPermissionImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "medicalExamination":
          employee.getPermissions().getMedicalExamination().setImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "ohstests":
          employee.getPermissions().getOhstests().setImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "sanel":
          employee.getPermissions().getSanel().setImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
        case "studentCard":
          employee.getPermissions().getStudentCard().setImage(
              new Binary(BsonBinarySubType.BINARY, file.getBytes())
          );
          break;
      }
      employeeRepository.save(employee);
    } catch (IOException e) {
      log.error("Can not save file", e);
      throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    }
  }

  public Employee getEmployee(String id) {
    return employeeRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
  }

  public void updateEmployee(Employee employee, String id) {
    Employee employeeFromDb = getEmployee(id);
    employeeFromDb.setFirstName(employee.getFirstName());
    employeeFromDb.setSurName(employee.getSurName());
    employeeFromDb.setPesel(employee.getPesel());
    employeeFromDb.setYearOfBirthday(employee.getYearOfBirthday());
    employeeFromDb.getPersonalAddress().setBuildingNumber(employee.getPersonalAddress().getBuildingNumber());
    employeeFromDb.getPersonalAddress().setCity(employee.getPersonalAddress().getCity());
    employeeFromDb.getPersonalAddress().setCode(employee.getPersonalAddress().getCode());
    employeeFromDb.getPersonalAddress().setEmail(employee.getPersonalAddress().getEmail());
    employeeFromDb.getPersonalAddress().setPhone(employee.getPersonalAddress().getPhone());
    employeeFromDb.getPersonalAddress().setStreet(employee.getPersonalAddress().getStreet());
    employeeFromDb.getTaxOfficeAddress().setAddress(employee.getTaxOfficeAddress().getAddress());
    employeeFromDb.getDeal().setEndDate(employee.getDeal().getEndDate());
    employeeFromDb.getDeal().setPlace(employee.getDeal().getPlace());
    employeeFromDb.getDeal().setStartDate(employee.getDeal().getStartDate());
    employeeFromDb.getDeal().setDealType(employee.getDeal().getDealType());
    employeeFromDb.getPermissions().getLifeguard().setReleaseDate(employee.getPermissions().getLifeguard().getReleaseDate());
    employeeFromDb.getPermissions().getFirstAid().setRefreshedDate(employee.getPermissions().getFirstAid().getRefreshedDate());
    employeeFromDb.getPermissions().getFirstAid().setEndDate(employee.getPermissions().getFirstAid().getEndDate());
    employeeFromDb.getPermissions().getUsefulPermissions().setYachtSailor(employee.getPermissions().getUsefulPermissions().getYachtSailor());
    employeeFromDb.getPermissions().getUsefulPermissions().setSwimmingInstructor(employee.getPermissions().getUsefulPermissions().getSwimmingInstructor());
    employeeFromDb.getPermissions().getUsefulPermissions().setFrogman(employee.getPermissions().getUsefulPermissions().getFrogman());
    employeeFromDb.getPermissions().getUsefulPermissions().setHelmsman(employee.getPermissions().getUsefulPermissions().getHelmsman());
    employeeFromDb.getPermissions().setAnotherPermission(employee.getPermissions().getAnotherPermission());
    employeeFromDb.getPermissions().getMedicalExamination().setRefreshedDate(employee.getPermissions().getMedicalExamination().getRefreshedDate());
    employeeFromDb.getPermissions().getMedicalExamination().setEndDate(employee.getPermissions().getMedicalExamination().getEndDate());
    employeeFromDb.getPermissions().getOhstests().setRefreshedDate(employee.getPermissions().getOhstests().getRefreshedDate());
    employeeFromDb.getPermissions().getOhstests().setEndDate(employee.getPermissions().getOhstests().getEndDate());
    employeeFromDb.getPermissions().getSanel().setEndDate(employee.getPermissions().getSanel().getEndDate());
    employeeFromDb.getPermissions().getStudentCard().setEndDate(employee.getPermissions().getStudentCard().getEndDate());
    employeeRepository.save(employeeFromDb);
  }

  public List<EmployeeListItemDto> getAll() {
    List<Employee> all = employeeRepository.findAll();
    return all.stream().map(this::fromEmployee).collect(Collectors.toList());
  }

  private EmployeeListItemDto fromEmployee(Employee employee) {
    EmployeeListItemDto employeeListItemDto = new EmployeeListItemDto();
    String endDate = employee.getDeal().getEndDate();
    employeeListItemDto.setId(employee.getId());
    employeeListItemDto.setFirstName(employee.getFirstName());
    employeeListItemDto.setSurName(employee.getSurName());
    employeeListItemDto.setCity(employee.getDeal().getPlace());
    employeeListItemDto.setDealEndDate(endDate.split("T")[0]);

    return employeeListItemDto;
  }
}
