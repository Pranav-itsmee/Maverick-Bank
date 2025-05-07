package com.example.mb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mb.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
   
}