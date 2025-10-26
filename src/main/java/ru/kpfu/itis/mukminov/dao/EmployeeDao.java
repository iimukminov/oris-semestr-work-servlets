package ru.kpfu.itis.mukminov.dao;

import ru.kpfu.itis.mukminov.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    void save(Employee employee);
    Optional<Employee> findById(Long id);
    Optional<Employee> findByEmail(String email);
    List<Employee> findAll();
    void update(Employee employee);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    List<Employee> findByRole(String role);
}
