package com.example.employee.emp.Controllers;

import com.example.employee.emp.DTO.EmployeeDTO;
import com.example.employee.emp.Repositories.EmployeeRepository;
import com.example.employee.emp.Services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

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

    @DeleteMapping("/delete/{employeeID}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeID) {
        UUID uuid = UUID.fromString(employeeID);
        if(employeeService.deleteEmployeeById(uuid)){
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String employeeId) {
        UUID uuid = UUID.fromString(employeeId);
        return employeeService.getEmployeeById(uuid);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable String employeeId, @RequestBody @Valid EmployeeDTO employeeDTO){

        UUID uuid = UUID.fromString(employeeId);
        if(employeeService.updateEmployee(uuid, employeeDTO)){
            return new ResponseEntity<>("Details updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Either employeeID is wrong or some other error occurred", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(@RequestParam(required = false) String firstName, @RequestParam(required = false) Integer departmentNumber) {
        List<EmployeeDTO> employees = employeeService.searchEmployees(firstName, departmentNumber);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByStatus() {
        List<EmployeeDTO> employees = employeeService.getActiveEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PatchMapping("/changeStatus/{employeeId}")
    public ResponseEntity<String> changeEmployeeStatus(@PathVariable String employeeId, @RequestParam boolean status) {
        UUID uuid = UUID.fromString(employeeId);
        if (employeeService.changeEmployeeStatus(uuid, status)) {
            return new ResponseEntity<>("Employee status updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/in-active")
    public ResponseEntity<List<EmployeeDTO>> getInactiveEmployeesByStatus() {
        List<EmployeeDTO> employees = employeeService.getInactiveEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
