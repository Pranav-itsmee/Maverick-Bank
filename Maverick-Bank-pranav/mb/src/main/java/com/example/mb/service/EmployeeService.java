package com.example.mb.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.mb.exception.InvalidUsernameException;
import com.example.mb.model.Branch;
import com.example.mb.model.Department;
import com.example.mb.model.Employee;
import com.example.mb.model.User;
import com.example.mb.repository.AuthRepository;
import com.example.mb.repository.BranchRepository;
import com.example.mb.repository.DepartmentRepository;
import com.example.mb.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Path rootLocation = Paths.get("D:/Documents/MB/employee-documents");
    private final String uploadDir = "C:/uploads/employees/";

    /**
     * Adds a new employee to the database.
     */
    public Employee addEmployee(Employee employee) {
        logger.info("Adding a new employee with name: {}", employee.getName());
        return employeeRepository.save(employee);
    }

    /**
     * Updates an existing employee using a full employee object.
     */
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        logger.info("Updating employee with ID: {}", id);
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        updatedEmployee.setId(existing.getId());
        return employeeRepository.save(updatedEmployee);
    }

    /**
     * Updates an employee's details, including optional file uploads.
     */
    public Employee updateEmployee(
            Long id, String name, String email, String address, String mobileNo,
            String gender, Long branchId, Long departmentId, String dobStr,
            MultipartFile addressProof, MultipartFile governmentIdProof, MultipartFile profilePic
    ) throws IOException {

        logger.info("Updating employee with ID: {} and name: {}", id, name);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Parse gender
        Employee.Gender genderEnum = Employee.Gender.valueOf(gender.toUpperCase());

        // Parse date of birth
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr, formatter);
        } catch (Exception e) {
            logger.error("Invalid date format for DOB: {}", dobStr);
            throw new RuntimeException("Invalid date format, please use yyyy-MM-dd");
        }

        // Update basic info
        employee.setName(name);
        employee.setEmail(email);
        employee.setAddress(address);
        employee.setMobileNo(mobileNo);
        employee.setGender(genderEnum);
        employee.setDob(dob);

        // File handling
        if (addressProof != null) {
            String addressProofPath = saveFile(addressProof);
            employee.setAddress(addressProofPath);
        }
        if (governmentIdProof != null) {
            String govIdProofPath = saveFile(governmentIdProof);
            employee.setGovernmentIdProofUrl(govIdProofPath);
        }
        if (profilePic != null) {
            String profilePicPath = saveFile(profilePic);
            employee.setProfilePicUrl(profilePicPath);
        }

        // Set branch and department
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setBranch(branch);
        employee.setDepartment(department);

        logger.info("Employee updated: {}", employee.getEmail());
        return employeeRepository.save(employee);
    }

    /**
     * Saves a file to the server and returns its absolute path.
     */
    private String saveFile(MultipartFile file) throws IOException {
        Files.createDirectories(rootLocation); // Ensure directory exists
        String fileName = file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        logger.info("File saved: {}", destinationFile.toString());
        return destinationFile.toString();
    }

    /**
     * Deletes an employee by ID.
     */
    public boolean deleteEmployee(Long id) {
        logger.info("Attempting to delete employee with ID: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            logger.info("Employee deleted successfully.");
            return true;
        }
        logger.warn("Employee with ID {} not found.", id);
        return false;
    }

    /**
     * Retrieves an employee by ID.
     */
    public Optional<Employee> getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Retrieves all employees.
     */
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees.");
        return employeeRepository.findAll();
    }

    /**
     * Retrieves employees by name (case-insensitive, partial match).
     */
    public List<Employee> getEmployeesByName(String name) {
        logger.info("Searching for employees with name containing: {}", name);
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Retrieves employees by department ID.
     */
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        logger.info("Fetching employees for department ID: {}", departmentId);
        return employeeRepository.findByDepartmentId(departmentId);
    }

    /**
     * Adds an employee along with their uploaded documents and login credentials.
     */
    public Employee addEmployee(String name, String email, String address, String mobileNo, String gender,
                                Long branchId, Department.Role role, Long departmentId, String dobStr,
                                String password, MultipartFile addressProof, MultipartFile governmentIdProof,
                                MultipartFile profilePic) throws IOException {

        logger.info("Creating employee with email: {}", email);

        // Parse DOB
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr, formatter);
        } catch (Exception e) {
            logger.error("Invalid DOB format: {}", dobStr);
            throw new RuntimeException("Invalid date format, please use yyyy-MM-dd");
        }

        Employee.Gender genderEnum = Employee.Gender.valueOf(gender.toUpperCase());

        // Fetch branch and department
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Create directories
        Files.createDirectories(rootLocation);

        // Save files
        Path profilePicPath = rootLocation.resolve(profilePic.getOriginalFilename());
        Path governmentIdPath = rootLocation.resolve(governmentIdProof.getOriginalFilename());
        Path addressProofPath = rootLocation.resolve(addressProof.getOriginalFilename());

        Files.copy(profilePic.getInputStream(), profilePicPath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(governmentIdProof.getInputStream(), governmentIdPath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(addressProof.getInputStream(), addressProofPath, StandardCopyOption.REPLACE_EXISTING);

        logger.info("Documents uploaded for employee: {}", email);

        // Build employee
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setAddress(address);
        employee.setMobileNo(mobileNo);
        employee.setGender(genderEnum);
        employee.setDob(dob);
        employee.setProfilePicUrl(profilePicPath.toString());
        employee.setGovernmentIdProofUrl(governmentIdPath.toString());
        employee.setAddressProofUrl(addressProofPath.toString());
        employee.setBranch(branch);
        employee.setDepartment(department);

        // Create login credentials
        User user = new User();
        user.setUsername(email);
        user.setPassword(password); // Raw password
        user.setRole(department.getRole().name()); // Role from Department enum

        try {
            authService.signUp(user); // Handles encoding and saving user
            logger.info("User credentials created for: {}", email);
        } catch (InvalidUsernameException e) {
            logger.error("Failed to create user for {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to create user for employee: " + e.getMessage());
        }

        // Save employee to repository
        return employeeRepository.save(employee);
    }

    /**
     * Get employee by their email.
     */
    public Employee getEmployeeByEmail(String email) {
        logger.info("Fetching employee by email: {}", email);
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        return optionalEmployee.orElseThrow(() ->
                new RuntimeException("Employee not found with email: " + email));
    }
}
