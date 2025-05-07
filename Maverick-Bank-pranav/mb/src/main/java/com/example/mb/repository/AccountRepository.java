package com.example.mb.repository;

import com.example.mb.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerNameIgnoreCase(String name);
    List<Account> findByBranchId(Long branchId);
    List<Account> findByStatus(String status);
}
