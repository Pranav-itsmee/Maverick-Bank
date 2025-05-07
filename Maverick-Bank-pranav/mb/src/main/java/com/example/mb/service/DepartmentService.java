package com.example.mb.service;

import com.example.mb.model.Department;
import com.example.mb.model.Department.Role;
import com.example.mb.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class); // Logger initialization

    @Autowired
    private DepartmentRepository departmentRepository;

    // Add a new department
    public Department addDepartment(Department department) {
        logger.info("Adding new department with role: {}", department.getRole());
        return departmentRepository.save(department);
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        logger.info("Fetching all departments.");
        return departmentRepository.findAll();
    }

    // Get department by ID
    public Optional<Department> getDepartmentById(Long id) {
        logger.info("Fetching department with ID: {}", id);
        return departmentRepository.findById(id);
    }

    // Get departments by role
    public Optional<Department> getDepartmentsByRole(Role role) {
        logger.info("Fetching departments with role: {}", role);
        return departmentRepository.findByRole(role);
    }

    // Update an existing department
    public Department updateDepartment(Long id, Department departmentDetails) {
        logger.info("Updating department with ID: {}", id);
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            department.setRole(departmentDetails.getRole());
            logger.info("Department updated with new role: {}", department.getRole());
            return departmentRepository.save(department);
        }
        
        logger.error("Department with ID: {} not found for update.", id); // Log error if department is not found
        return null; // Or throw an exception if needed
    }

    // Delete a department by ID
    public void deleteDepartment(Long id) {
        logger.info("Deleting department with ID: {}", id);
        departmentRepository.deleteById(id);
    }
}
