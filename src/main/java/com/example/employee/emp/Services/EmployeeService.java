package com.example.employee.emp.Services;

import com.example.employee.emp.DTO.EmployeeDTO;
import com.example.employee.emp.Entities.EmployeeEntity;
import com.example.employee.emp.Repositories.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeDTO addEmployee(@Valid EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(UUID id) {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public ResponseEntity<EmployeeDTO> getEmployeeById(UUID employeeID) {
        if(employeeRepository.existsById(employeeID)){
            return ResponseEntity.ok(modelMapper.map(employeeRepository.findById(employeeID), EmployeeDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    public boolean updateEmployee(UUID employeeID, @Valid EmployeeDTO employeeDTO) {
        if(employeeRepository.existsById(employeeID)){
            EmployeeEntity employeeEntity = employeeRepository.findById(employeeID).orElse(null);
            employeeEntity.setFirstName(employeeDTO.getFirstName());
            employeeEntity.setLastName(employeeDTO.getLastName());
            employeeEntity.setEmail(employeeDTO.getEmail());
            employeeEntity.setActive(employeeDTO.isActive());
            employeeEntity.setDesignation(employeeDTO.getDesignation());
            employeeEntity.setAdmin(employeeDTO.isAdmin());
            employeeEntity.setDepartmentNumber(employeeDTO.getDepartmentNumber());
            employeeEntity.setAddress(employeeDTO.getAddress());
            employeeEntity.setPhoneNumber(employeeDTO.getPhoneNumber());

            employeeRepository.save(employeeEntity);

            return true;
        }
        return false;
    }

    public List<EmployeeDTO> searchEmployees(String name, Integer department) {
        List<EmployeeEntity> employees = new ArrayList<>();
        if (department == null){
            employees = employeeRepository.findByFirstName(name);
        }
        else if(name == null){
            employees = employeeRepository.findByDepartmentNumber(department);
        }
        else {
            employees = employeeRepository.findByFirstNameOrDepartmentNumber(name, department);
        }
        return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getActiveEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findByActiveTrue();
        return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public boolean changeEmployeeStatus(UUID uuid, boolean status) {
        if (employeeRepository.existsById(uuid)) {
            EmployeeEntity employeeEntity = employeeRepository.findById(uuid).orElse(null);
            employeeEntity.setActive(status);
            employeeRepository.save(employeeEntity);
            return true;
        }
        return false;
    }
}
