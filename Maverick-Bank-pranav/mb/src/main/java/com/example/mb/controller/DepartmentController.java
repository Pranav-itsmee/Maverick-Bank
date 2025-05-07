package com.example.mb.controller;

import com.example.mb.model.Department;
import com.example.mb.model.Department.Role;
import com.example.mb.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/departments")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * Add a new Department.
     * Logs the incoming request and successful department creation.
     */
    @PostMapping("/add")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        logger.info("POST /api/departments/add called with department data: {}", department);
        Department savedDepartment = departmentService.addDepartment(department);
        logger.info("Department created with ID: {}", savedDepartment.getId());
        return ResponseEntity.ok(savedDepartment);
    }

    /**
     * Get all Departments.
     * Logs the request and the number of departments fetched.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Department>> getAllDepartments() {
        logger.info("GET /api/departments/getAll called");
        List<Department> departments = departmentService.getAllDepartments();
        logger.info("Fetched {} departments", departments.size());
        return ResponseEntity.ok(departments);
    }

    /**
     * Get a Department by ID.
     * Logs the request and whether the department is found or not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        logger.info("GET /api/departments/{} called", id);
        Optional<Department> department = departmentService.getDepartmentById(id);
        return department.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Department not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Get Departments by Role.
     * Logs the role and the list of departments associated with that role.
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<Optional<Department>> getDepartmentsByRole(@PathVariable Role role) {
        logger.info("GET /api/departments/role/{} called", role);
        Optional<Department> departments = departmentService.getDepartmentsByRole(role);
        logger.info("Fetched {} departments with role: {}", role);
        return ResponseEntity.ok(departments);
    }

    /**
     * Update a Department.
     * Logs the request and the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        logger.info("PUT /api/departments/{} called with updated data: {}", id, department);
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        if (updatedDepartment != null) {
            logger.info("Department with ID {} updated successfully", id);
            return ResponseEntity.ok(updatedDepartment);
        } else {
            logger.warn("Department with ID {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a Department.
     * Logs the request and successful deletion of the department.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        logger.info("DELETE /api/departments/{} called", id);
        departmentService.deleteDepartment(id);
        logger.info("Department with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
