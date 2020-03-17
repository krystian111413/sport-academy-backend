package com.sportacademy.project.employees;

import java.io.IOException;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeService {

  private EmployeeRepository employeeRepository;
  private StorageService storageService;

  public void storeFileForEmployee(MultipartFile file, String id) {
    Employee employee = getEmployee(id);
    try {
      employee.getPermissions().getLifeguard().setImage(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())
      );
      employeeRepository.save(employee);
    } catch (IOException e) {
      log.error("Can not save file", e);
      throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    }
  }

  public Employee getEmployee(String id) {
    return employeeRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
  }

}
