package com.romeo.springboot.rest.api.service;


import com.romeo.springboot.rest.api.dto.EmployeeDTO;
import com.romeo.springboot.rest.api.exception.ResourceNotFoundException;
import com.romeo.springboot.rest.api.model.Employee;
import com.romeo.springboot.rest.api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> new EmployeeDTO(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail()
                )).collect(Collectors.toList());
    }

    public EmployeeDTO findEmployee(Long employeeId) throws ResourceNotFoundException {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        return convertToDTO(employee);
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        var employee = convertToEntity(employeeDTO);
        employeeRepository.save(employee);

        return convertToDTO(employee);
    }

    public EmployeeDTO updateEmployee(long employeeId, EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id: '{}' " + employeeId));
        updatedEmployee(employeeDTO, employee);
        var savedEmployee = employeeRepository.save(employee);

        return convertToDTO(savedEmployee);
    }

    private void updatedEmployee(EmployeeDTO employeeDTO, Employee employee) {
        var updatedEmployee = convertToEntity(employeeDTO);
        employee.setId(updatedEmployee.getId());
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );

    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.employeeId(),
                employeeDTO.firstName(),
                employeeDTO.lastName(),
                employeeDTO.email()
        );
    }
}
