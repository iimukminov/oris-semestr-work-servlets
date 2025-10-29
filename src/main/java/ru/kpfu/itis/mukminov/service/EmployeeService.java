package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.dto.EmployeeDto;
import ru.kpfu.itis.mukminov.enums.Role;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    void registerEmployee(String name, String lastname, String email, String password, Role role, String position);

    boolean authenticate(String email, String password);

    Optional<EmployeeDto> findById(Long id);

    Optional<EmployeeDto> findByEmail(String email);

    List<EmployeeDto> findAll();

    List<EmployeeDto> getEmployeesByRole(String role);

    void updateEmployee(Long id, String name, String lastname, String email, Role role, String position, String password);

    void deleteEmployee(Long id);

    boolean isEmailTaken(String email);
}
