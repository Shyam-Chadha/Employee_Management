package com.example.employee.emp.Services;

import com.example.employee.emp.DTO.EmployeeDTO;
import com.example.employee.emp.Entities.EmployeeEntity;
import com.example.employee.emp.Repositories.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
