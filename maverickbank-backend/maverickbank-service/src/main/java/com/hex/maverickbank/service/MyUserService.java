package com.hex.maverickbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hex.maverickbank.repository.AuthRepository;
@Service
public class MyUserService implements UserDetailsService{

	@Autowired
	private AuthRepository authRepository;
	/*
	 * As soon as my class MyUserService implement UserDetailsService, 
	 * it becomes UserDetailsService as per inheritance is-a relation rule
	 * 
	 * ex. 
	 * class B implements A{} 
	 * B is-a A 
	 * 
	 * class dog implements Animal{}
	 * Dog is-a Animal 
	 * 
	 * class MyUserService implements UserDetailsService
	 * MyUserService is-a UserDetailsService
	 * 
	 * and do note that security config needs UserDetailsService
	 */
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = authRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return user;
	}
}
