package com.sportacademy.project.employees;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class EmployeesController {

  private EmployeeRepository employeeRepository;
  private StorageService storageService;
  private EmployeeService employeeService;

  @GetMapping
  public Iterable<Employee> getAll() {
    return employeeRepository.findAll();
  }

  @GetMapping("/{id}")
  public Employee getOne(@PathVariable String id) {
    return employeeRepository.findById(id).orElseGet(null);
  }

  @PostMapping("/{id}/file")
  public void handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable String id) {
    employeeService.storeFileForEmployee(file, id);
  }

  @GetMapping(value = "/{id}/file")
  public void getFile(
      @PathVariable("id") String id,
      HttpServletResponse response) {
    try {
      Employee employee = employeeService.getEmployee(id);
      InputStream file = new FileInputStream(employee.getPermissions().getLifeguard().getFilePath());
      org.apache.commons.io.IOUtils.copy(file, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException ex) {
      log.info("Error writing file to output stream", ex);
      throw new RuntimeException("IOError writing file to output stream");
    }

  }


  @PostMapping
  public Employee create(@RequestBody Employee employee) {
    Employee empl = this.employeeRepository.insert(employee);
    return empl;
  }

  @PutMapping("/{id}")
  public void replace(@RequestBody Employee employee, @PathVariable String id) {
    employee.setId(id);
    this.employeeRepository.save(employee);
  }
}
