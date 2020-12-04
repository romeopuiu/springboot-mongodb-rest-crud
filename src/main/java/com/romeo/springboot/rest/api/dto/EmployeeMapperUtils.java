package com.romeo.springboot.rest.api.dto;

import com.romeo.springboot.rest.api.model.Employee;
import org.springframework.stereotype.Service;


@Service
public class EmployeeMapperUtils {

    public EmployeeDTO toEmployeeDTO(Employee employee) {
        return EmployeeDTO.builder()
                .employeeId(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .emailId(employee.getEmailId())
                .build();
    }

    public void copyToEmployee(EmployeeDTO employeeDTO,
                               Employee employee) {

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmailId(employeeDTO.getEmailId());

    }
}
