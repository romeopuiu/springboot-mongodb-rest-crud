package com.romeo.springboot.rest.api.repository;

import com.romeo.springboot.rest.api.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EmployeeRepository extends MongoRepository<Employee, Long>{

}
