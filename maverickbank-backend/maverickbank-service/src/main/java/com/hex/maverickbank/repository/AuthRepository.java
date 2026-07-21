package com.hex.maverickbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hex.maverickbank.entity.User;

public interface AuthRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
