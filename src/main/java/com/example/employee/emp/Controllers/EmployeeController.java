package com.example.employee.emp.Controllers;

import com.example.employee.emp.DTO.EmployeeDTO;
import com.example.employee.emp.Entities.EmployeeEntity;
import com.example.employee.emp.Services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Object> addEmployee(@RequestBody @Valid EmployeeDTO  employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
        EmployeeDTO newEmp = employeeService.addEmployee(employeeDTO);
        return new ResponseEntity<>(newEmp, HttpStatus.CREATED);
    }
}
