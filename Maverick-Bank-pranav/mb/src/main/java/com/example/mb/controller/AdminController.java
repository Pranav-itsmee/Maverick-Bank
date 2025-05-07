package com.example.mb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mb.model.Admin;
import com.example.mb.model.Department.Role;
import com.example.mb.service.AdminService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    /**
     * Create a new Admin.
     * Logs the request and checks if the department's role is REGIONAL_MANAGER.
     * Throws an exception if the role is invalid and logs the error.
     * Logs the admin information once created successfully.
     */
    @PostMapping("/add")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        logger.info("POST /api/admins/add called with admin: {}", admin);
        
        // Check if department role is REGIONAL_MANAGER
        if (admin.getDepartment() == null || admin.getDepartment().getRole() != Role.REGIONAL_MANAGER) {
            logger.error("Invalid department role for admin: {}", admin);
            throw new IllegalArgumentException("Only REGIONAL_MANAGER role can be assigned to Admin.");
        }

        // Creating the admin
        Admin saved = adminService.addAdmin(admin);
        logger.info("Admin created with ID: {}", saved.getId());

        return ResponseEntity.ok(saved);
    }
}
