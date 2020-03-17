package com.sportacademy.project.employees;

import java.util.Base64;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
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
  public String getFile(@PathVariable String id, Model model) {
    Employee employee = employeeService.getEmployee(id);
    return Base64.getEncoder()
        .encodeToString(employee.getPermissions().getLifeguard().getImage().getData());
  }

  @PostMapping
  public Employee create(@RequestBody Employee employee) {
    Employee empl = this.employeeRepository.insert(employee);
    return empl;
  }

  @PutMapping("/{id}")
  public void replace(@RequestBody Employee employee, @PathVariable String id) {
    Employee employeeFromDb = employeeService.getEmployee(id);
    employee.getPermissions().getLifeguard().setImage(
        employeeFromDb.getPermissions().getLifeguard().getImage()
    );
    employeeRepository.save(employee);
  }
}
