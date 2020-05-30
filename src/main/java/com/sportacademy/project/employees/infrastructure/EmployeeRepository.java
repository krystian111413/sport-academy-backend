package com.sportacademy.project.employees.infrastructure;

import com.sportacademy.project.employees.domain.Employee;
import com.sportacademy.project.employees.domain.EmployeeListItemDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query(value="{  }", fields="{ 'id' : 1, 'firstName' : 1, 'surName' : 1, 'deal' : 1}")
    List<Employee> findAllLess();

}
