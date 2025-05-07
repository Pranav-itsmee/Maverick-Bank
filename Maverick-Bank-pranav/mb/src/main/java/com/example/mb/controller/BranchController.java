package com.example.mb.controller;

import com.example.mb.model.Branch;
import com.example.mb.service.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @Autowired
    private BranchService branchService;

    /**
     * Create a new Branch.
     * Logs the request and successful creation of the branch.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createBranch(@RequestBody Branch branch) {
        logger.info("POST /api/branches/add called with branch: {}", branch);
        Branch createdBranch = branchService.createBranch(branch);
        logger.info("Branch created with ID: {}", createdBranch.getId());
        return ResponseEntity.ok(createdBranch);
    }

    /**
     * Get a Branch by its ID.
     * Logs the request and whether the branch is found or not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable Long id) {
        logger.info("GET /api/branches/{} called", id);
        Branch branch = branchService.getBranchById(id);
        if (branch != null) {
            logger.info("Branch found with ID: {}", id);
            return ResponseEntity.ok(branch);
        } else {
            logger.warn("Branch not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all Branches.
     * Logs the request to fetch all branches.
     */
    @GetMapping("/getAll")
    public List<Branch> getAllBranches() {
        logger.info("GET /api/branches/getAll called");
        List<Branch> branches = branchService.getAllBranches();
        logger.info("Fetched {} branches", branches.size());
        return branches;
    }

    /**
     * Update a Branch by its ID.
     * Logs the request to update and whether the update is successful.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable Long id, @RequestBody Branch branch) {
        logger.info("PUT /api/branches/{} called to update with data: {}", id, branch);
        Branch updated = branchService.updateBranch(id, branch);
        if (updated != null) {
            logger.info("Branch updated with ID: {}", id);
            return ResponseEntity.ok(updated);
        } else {
            logger.warn("Branch not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a Branch by its ID.
     * Logs the request to delete and confirms successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable Long id) {
        logger.info("DELETE /api/branches/{} called", id);
        branchService.deleteBranch(id);
        logger.info("Branch deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
