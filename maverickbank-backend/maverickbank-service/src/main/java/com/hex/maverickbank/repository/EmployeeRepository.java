package com.hex.maverickbank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hex.maverickbank.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByDepartmentId(Long departmentId);
    Optional<Employee> findByEmail(String email);
}
