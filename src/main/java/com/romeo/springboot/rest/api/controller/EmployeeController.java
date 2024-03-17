package com.romeo.springboot.rest.api.controller;

import com.romeo.springboot.rest.api.dto.EmployeeDTO;
import com.romeo.springboot.rest.api.exception.ResourceNotFoundException;
import com.romeo.springboot.rest.api.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(value = "id") final Long id)
            throws ResourceNotFoundException {
        log.info("Employee id : '{}'", id);

        return ResponseEntity.ok(employeeService.findEmployee(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Employee is: '{}'", employeeDTO);
        return ResponseEntity.ok(employeeService.addEmployee(employeeDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") Long id,
                                                      @Valid @RequestBody EmployeeDTO employeeDTO)
            throws ResourceNotFoundException {

        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable(value = "id") final Long id) {
        employeeService.deleteEmployee(id);
    }
}
