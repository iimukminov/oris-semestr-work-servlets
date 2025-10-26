package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.EmployeeDao;
import ru.kpfu.itis.mukminov.dto.EmployeeDto;
import ru.kpfu.itis.mukminov.entity.Employee;
import ru.kpfu.itis.mukminov.enums.Role;
import ru.kpfu.itis.mukminov.service.EmployeeService;
import ru.kpfu.itis.mukminov.util.PasswordUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void registerEmployee(String name, String lastname, String email, String password, Role role, String position) {
        if (isEmailTaken(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        String salt = PasswordUtil.generateSalt();
        String passwordHash = PasswordUtil.hashPassword(password, salt);

        Employee employee = new Employee(null, name, lastname, email, passwordHash, salt, role, position);

        employeeDao.save(employee);
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<Employee> employeeOpt = employeeDao.findByEmail(email);

        if (employeeOpt.isEmpty()) {
            return false;
        }

        Employee employee = employeeOpt.get();
        String hashedPassword = PasswordUtil.hashPassword(password, employee.getPasswordSalt());

        return hashedPassword.equals(employee.getPasswordHash());
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return employeeDao.findById(id).map(this::convertToDto);
    }

    @Override
    public Optional<EmployeeDto> findByEmail(String email) {
        return employeeDao.findByEmail(email).map(this::convertToDto);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeDao.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getEmployeesByRole(String role) {
        return employeeDao.findByRole(role).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEmployee(Long id, String name, String lastname, String email, Role role, String position) {
        Optional<Employee> existingEmployee = employeeDao.findById(id);

        if (existingEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }

        Employee employee = existingEmployee.get();
        Employee updatedEmployee = new Employee(
                employee.getId(),
                name,
                lastname,
                email,
                employee.getPasswordHash(),
                employee.getPasswordSalt(),
                role,
                position
        );

        employeeDao.update(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeDao.deleteById(id);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return employeeDao.existsByEmail(email);
    }

    private EmployeeDto convertToDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getRole(),
                employee.getPosition()
        );
    }
}
