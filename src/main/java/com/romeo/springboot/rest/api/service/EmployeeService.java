package com.romeo.springboot.rest.api.service;


import com.romeo.springboot.rest.api.dto.EmployeeDTO;
import com.romeo.springboot.rest.api.dto.EmployeeMapperUtils;
import com.romeo.springboot.rest.api.model.Employee;
import com.romeo.springboot.rest.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapperUtils employeeMapperUtils;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeMapperUtils employeeMapperUtils) {
        this.employeeRepository = employeeRepository;
        this.employeeMapperUtils = employeeMapperUtils;
    }

    public List<Employee> getEmployess() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {

        return saveAndFlush(employeeDTO, new Employee());
    }

    private EmployeeDTO saveAndFlush(EmployeeDTO categoryDTO, Employee employee) {

        employeeMapperUtils.copyToEmployee(categoryDTO, employee);
        return employeeMapperUtils.toEmployeeDTO(employeeRepository.save(employee));
    }

    public Optional<EmployeeDTO> updateEmployee(long employeeId, EmployeeDTO employeeDTO) {

        return findEmployee(employeeId)
                .map(empl -> {
                    EmployeeDTO result = saveAndFlush(employeeDTO, empl);

                    return result;
                });
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

}
