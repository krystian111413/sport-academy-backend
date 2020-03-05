package com.sportacademy.project.employees;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin
@AllArgsConstructor
public class EmployeesController {

  private EmployeeRepository employeeRepository;

  @GetMapping
  public Iterable<Employee> getAll() {
    return employeeRepository.findAll();
  }

  @GetMapping("/{id}")
  public Employee getOne(@PathVariable String id) {
    return employeeRepository.findById(id).orElseGet(null);
  }

  @PostMapping
  public void create(@RequestBody Employee employee) {
    this.employeeRepository.insert(employee);
  }

  @PutMapping("/{id}")
  public void replace(@RequestBody Employee employee, @PathVariable String id) {
    employee.setId(id);
    this.employeeRepository.save(employee);
  }





}
