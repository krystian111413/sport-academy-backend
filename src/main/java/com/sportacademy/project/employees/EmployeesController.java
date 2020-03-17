package com.sportacademy.project.employees;

import com.sportacademy.project.auth.AuthService;
import java.util.Base64;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public Iterable<Employee> getAll(@RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      return employeeRepository.findAll();
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  @GetMapping("/{id}")
  public Employee getOne(@PathVariable String id, @RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      return employeeRepository.findById(id).orElseGet(null);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/{id}/file")
  public void handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable String id, @RequestHeader("Authorization") String auth) {
    employeeService.storeFileForEmployee(file, id);
  }

  @GetMapping(value = "/{id}/file")
  public String getFile(@PathVariable String id, Model model, @RequestHeader("Authorization") String auth) {
    if (authService.isAuthorization(auth)) {
      Employee employee = employeeService.getEmployee(id);
      return Base64.getEncoder()
          .encodeToString(employee.getPermissions().getLifeguard().getImage().getData());
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
      Employee employeeFromDb = employeeService.getEmployee(id);
      employee.getPermissions().getLifeguard().setImage(
          employeeFromDb.getPermissions().getLifeguard().getImage()
      );
      employeeRepository.save(employee);
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

  }
}
