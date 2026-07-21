package com.hex.maverickbank.repository;

import com.hex.maverickbank.entity.Upi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpiRepository extends JpaRepository<Upi, Long> {
}
