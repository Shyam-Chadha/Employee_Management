package com.example.employee.emp.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private UUID id;
    @NotBlank(message = "First Name is required")
    private String firstName;
    private String lastName;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    private String phoneNumber;
    private String address;
    private String designation;
    @NotNull(message = "Department Number is required")
    private int departmentNumber;
    private boolean admin;
    private boolean active;
}
