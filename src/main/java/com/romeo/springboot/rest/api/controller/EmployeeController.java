package com.romeo.springboot.rest.api.controller;

import com.romeo.springboot.rest.api.dto.EmployeeDTO;
import com.romeo.springboot.rest.api.exception.ResourceNotFoundException;
import com.romeo.springboot.rest.api.model.Employee;
import com.romeo.springboot.rest.api.service.EmployeeService;
import com.romeo.springboot.rest.api.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              SequenceGeneratorService sequenceGeneratorService) {
        this.employeeService = employeeService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @GetMapping("/employees")
    public HttpEntity getAllEmployees() {
        return ResponseEntity.ok(employeeService.getEmployess());
    }

    @GetMapping("/employees/{id}")
    public HttpEntity getEmployeeById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Employee employee = employeeService.findEmployee(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setEmployeeId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
        return ResponseEntity.ok(employeeService.addEmployee(employeeDTO));
    }

    @PutMapping(value = "/employees/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity updateEmployee(@PathVariable(value = "id") Long id,
                                     @Valid @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        return employeeService.findEmployee(id)
                .map(employee -> {
                    employeeService.updateEmployee(id, employeeDTO);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Employee employee = employeeService.findEmployee(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        employeeService.deleteEmployee(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
