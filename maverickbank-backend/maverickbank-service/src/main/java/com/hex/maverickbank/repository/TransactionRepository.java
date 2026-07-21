package com.hex.maverickbank.repository;

import com.hex.maverickbank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccountId(Long accountId);
    List<Transaction> findByTransactionTypeIgnoreCase(String transactionType);

    // Returns Double (not BigDecimal) because Transaction.amount is stored as Double.
    @Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transaction t "
            + "WHERE t.fromAccount.id = :accountId AND t.status = 'SUCCESS' "
            + "AND t.transactionDate >= :start AND t.transactionDate < :end")
    Double sumSuccessfulAmountByAccountAndDateRange(@Param("accountId") Long accountId,
                                                     @Param("start") LocalDateTime start,
                                                     @Param("end") LocalDateTime end);
}
