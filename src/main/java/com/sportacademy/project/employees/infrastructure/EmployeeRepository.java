package com.sportacademy.project.employees.infrastructure;

import com.sportacademy.project.employees.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
