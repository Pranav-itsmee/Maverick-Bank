package com.hex.maverickbank.repository;

import com.hex.maverickbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerNameIgnoreCase(String name);
    List<Account> findByBranchId(Long branchId);
    List<Account> findByStatus(String status);
    Optional<Account> findByAccountNumber(String accountNumber);
}
