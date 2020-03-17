package com.sportacademy.project.employees;

import java.nio.file.Path;
import javax.xml.ws.http.HTTPException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    storageService.removeFile(employee.getPermissions().getLifeguard().getFilePath());
    Path path = storageService.storeFile(file, id);
    employee.getPermissions().getLifeguard().setFilePath(path.toString());
    employeeRepository.save(employee);
  }

  public Employee getEmployee(String id) {
    return employeeRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
  }

}
