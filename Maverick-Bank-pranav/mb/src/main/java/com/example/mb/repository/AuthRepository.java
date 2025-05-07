package com.example.mb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mb.model.User;

public interface AuthRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
