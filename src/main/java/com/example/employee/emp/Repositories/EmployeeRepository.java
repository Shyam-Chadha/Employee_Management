package com.example.employee.emp.Repositories;

import com.example.employee.emp.DTO.EmployeeDTO;
import com.example.employee.emp.Entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    List<EmployeeEntity> findByFirstNameOrDepartmentNumber(String name, int department);

    List<EmployeeEntity> findByFirstName(String name);

    List<EmployeeEntity> findByDepartmentNumber(Integer department);

    List<EmployeeEntity> findByActiveTrue();
}
