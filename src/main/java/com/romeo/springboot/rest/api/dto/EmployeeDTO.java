package com.romeo.springboot.rest.api.dto;


public record EmployeeDTO(long employeeId,
                          String firstName,
                          String lastName,
                          String email
) {
}
