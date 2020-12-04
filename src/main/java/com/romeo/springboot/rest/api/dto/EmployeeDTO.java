package com.romeo.springboot.rest.api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDTO {

    private long employeeId;
    private String firstName;
    private String lastName;
    private String emailId;

}
