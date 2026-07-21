package com.hex.maverickbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hex.maverickbank.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Optional<Department> findByRole(Department.Role role);

}
