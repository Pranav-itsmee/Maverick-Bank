package com.example.mb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mb.exception.InvalidUsernameException;
import com.example.mb.model.User;
import com.example.mb.repository.AuthRepository;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authRepository;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	public User signUp(User user) throws InvalidUsernameException {
		//check if Username is unique 
		User user1 =  authRepository.findByUsername(user.getUsername());
		if(user1 != null) { 
			//if user exists it will be not null. if its a new username then it will be null 
			throw new InvalidUsernameException("Username already exists");
		}
		/*Give role USER_DEFAULT if not provided */
		if(user.getRole() == null)
			user.setRole(user.getRole());
		
		//encode the password 
		String encodedPass = bcrypt.encode(user.getPassword());
		
		//attach encoded pass to user 
		user.setPassword(encodedPass);
		
		return authRepository.save(user);
	}
	
	

}
