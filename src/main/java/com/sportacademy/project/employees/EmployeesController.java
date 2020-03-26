package com.sportacademy.project.employees;

import com.sportacademy.project.auth.AuthService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class EmployeesController {

  private EmployeeRepository employeeRepository;
  private AuthService authService;
  private EmployeeService employeeService;

  @GetMapping
  @ResponseBody
  public List<EmployeeListItemDto> getAll(@RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      return employeeService.getAll();
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  @GetMapping("/{id}")
  public Employee getOne(@PathVariable String id, @RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      return employeeService.getEmployee(id);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/{id}/file/{fileName}")
  public void handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable String id, @PathVariable String fileName,
      @RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      employeeService.storeFileForEmployee(file, id, fileName);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping
  public Employee create(@RequestBody Employee employee, @RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      Employee empl = this.employeeRepository.insert(employee);
      return empl;
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

  }

  @PutMapping("/{id}")
  public void replace(@RequestBody Employee employee, @PathVariable String id, @RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      employeeService.updateEmployee(employee, id);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id, @RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      employeeService.delete(id);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }
}
