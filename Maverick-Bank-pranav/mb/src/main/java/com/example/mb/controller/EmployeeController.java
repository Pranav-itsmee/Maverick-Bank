package com.example.mb.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.mb.model.Department;
import com.example.mb.model.Employee;
import com.example.mb.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    
    
    /**
     * Get all employees.
     * Logs the request and the number of employees fetched.
     */
    @GetMapping("/getAll")
    public List<Employee> getAllEmployees() {
        logger.info("GET /api/employees/getAll called");
        List<Employee> employees = employeeService.getAllEmployees();
        logger.info("Fetched {} employees", employees.size());
        return employees;
    }

    /**
     * Get employee by ID.
     * Logs the request and whether the employee was found or not.
     */
    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable("id") Long id) {
        logger.info("GET /api/employees/{} called", id);
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            logger.info("Employee found with ID: {}", id);
        } else {
            logger.warn("Employee with ID {} not found", id);
        }
        return employee;
    }

    /**
     * Add a new employee.
     * Logs the incoming request and the successful addition of the employee.
     */
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("mobileNo") String mobileNo,
            @RequestParam("gender") String gender,
            @RequestParam("branchId") Long branchId,
            @RequestParam("role") Department.Role role,
            @RequestParam("departmentId") Long departmentId,
            @RequestParam("dobStr") String dobStr,
            @RequestParam("password") String password,
            @RequestParam("addressProof") MultipartFile addressProof,
            @RequestParam("governmentIdProof") MultipartFile governmentIdProof,
            @RequestParam("profilePic") MultipartFile profilePic
    ) throws Exception {

        Employee.Gender genderstr = Employee.Gender.valueOf(gender.toUpperCase());

        Employee savedEmployee = employeeService.addEmployee(
                name, email, address, mobileNo, gender, branchId, role, departmentId,
                dobStr, password, addressProof, governmentIdProof, profilePic
        );

        return ResponseEntity.ok(savedEmployee);
    }


    /**
     * Update an existing employee.
     * Logs the request and the successful update of the employee.
     */
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
        logger.info("PUT /api/employees/{} called with updated data: {}", id, employee);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        logger.info("Employee with ID {} updated successfully", id);
        return updatedEmployee;
    }

    /**
     * Update employee with files (e.g., profile picture, address proof).
     * Logs the request and the update process with additional files.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("mobileNo") String mobileNo,
            @RequestParam("gender") String gender,
            @RequestParam("branchId") Long branchId,
            @RequestParam("departmentId") Long departmentId,
            @RequestParam("dobStr") String dobStr,
            @RequestParam(value = "addressProof", required = false) MultipartFile addressProof,
            @RequestParam(value = "governmentIdProof", required = false) MultipartFile governmentIdProof,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic
    ) throws IOException {

        // Call the service layer to update the employee
        Employee updatedEmployee = employeeService.updateEmployee(
                id, name, email, address, mobileNo, gender, branchId, departmentId,
                dobStr, addressProof, governmentIdProof, profilePic
        );

        // Log the successful update
        logger.info("Employee with ID {} updated successfully", id);

        // Return the updated employee in the response
        return ResponseEntity.ok(updatedEmployee);
    }


    /**
     * Delete an employee by ID.
     * Logs the request and the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public boolean deleteEmployee(@PathVariable("id") Long id) {
        logger.info("DELETE /api/employees/{} called", id);
        boolean result = employeeService.deleteEmployee(id);
        if (result) {
            logger.info("Employee with ID {} deleted successfully", id);
        } else {
            logger.warn("Failed to delete employee with ID {}", id);
        }
        return result;
    }
    
    @GetMapping("/email/{email}")
    public Employee getEmployeeByEmail(@PathVariable("email") String email) {
        return employeeService.getEmployeeByEmail(email);
    }
    
    
}
