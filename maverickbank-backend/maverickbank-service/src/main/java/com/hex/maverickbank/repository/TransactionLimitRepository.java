package com.hex.maverickbank.repository;

import com.hex.maverickbank.entity.TransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, Long> {
}
