package com.hex.maverickbank.repository;

import com.hex.maverickbank.entity.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {
}
