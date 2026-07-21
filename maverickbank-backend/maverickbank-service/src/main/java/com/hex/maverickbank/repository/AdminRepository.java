package com.hex.maverickbank.repository;

import com.hex.maverickbank.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
