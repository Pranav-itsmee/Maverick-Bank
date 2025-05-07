package com.example.mb.service;

import com.example.mb.model.Branch;
import com.example.mb.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    private static final Logger logger = LoggerFactory.getLogger(BranchService.class); // Logger initialization

    @Autowired
    private BranchRepository branchRepository;

    // Create a new branch
    public Branch createBranch(Branch branch) {
        logger.info("Creating a new branch with name: {}", branch.getBranchName());
        return branchRepository.save(branch);
    }

    // Get a branch by its ID
    public Branch getBranchById(Long id) {
        logger.info("Fetching branch with ID: {}", id);
        return branchRepository.findById(id).orElse(null); // Returning null if not found
    }

    // Get all branches
    public List<Branch> getAllBranches() {
        logger.info("Fetching all branches.");
        return branchRepository.findAll();
    }

    // Update an existing branch
    public Branch updateBranch(Long id, Branch branch) {
        logger.info("Updating branch with ID: {}", id);

        Optional<Branch> existing = branchRepository.findById(id);
        if (existing.isPresent()) {
            Branch b = existing.get();
            // Update the branch details
            b.setBranchName(branch.getBranchName());
            b.setBranchAddress(branch.getBranchAddress());
            logger.info("Branch with ID: {} successfully updated.", id);
            return branchRepository.save(b);
        } else {
            logger.warn("Branch with ID: {} not found for update.", id);
            return null; // Return null if branch is not found
        }
    }

    // Delete a branch by its ID
    public void deleteBranch(Long id) {
        logger.info("Deleting branch with ID: {}", id);
        branchRepository.deleteById(id);
        logger.info("Branch with ID: {} successfully deleted.", id);
    }
}
