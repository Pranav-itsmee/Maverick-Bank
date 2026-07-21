package com.hex.maverickbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hex.maverickbank.entity.Admin;
import com.hex.maverickbank.entity.Department.Role;
import com.hex.maverickbank.repository.AdminRepository;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class); // Logger initialization

    @Autowired
    private AdminRepository adminRepository;

    // Method to add a new Admin with validation on the role
    public Admin addAdmin(Admin admin) {
        // Validate if the admin has the REGIONAL_MANAGER role
        if (admin.getDepartment() == null || admin.getDepartment().getRole() != Role.REGIONAL_MANAGER) {
            logger.error("Failed to add admin. Only REGIONAL_MANAGER role can be assigned to Admin.");
            throw new IllegalArgumentException("Only REGIONAL_MANAGER role can be assigned to Admin.");
        }

        logger.info("Attempting to add a new admin with role: {}", admin.getDepartment().getRole());

        // Save the admin and return the saved entity
        Admin savedAdmin = adminRepository.save(admin);
        logger.info("Successfully added admin with ID: {}", savedAdmin.getId());

        return savedAdmin;
    }
}
