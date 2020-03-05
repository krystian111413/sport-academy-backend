package com.sportacademy.project.employees;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
