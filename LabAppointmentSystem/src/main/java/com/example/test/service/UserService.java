package com.example.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.model.UserModel;
import com.example.test.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	//Authenticate User
	public boolean authenticateUser(String email, String password) {
		UserModel user = userRepository.findByEmail(email);
		return user != null && user.getPassword().equals(password);
	}

	// Check if email exists
	public boolean emailExists(String email) {
		UserModel user = userRepository.findByEmail(email);
		return user != null;
	}

	//Save User
	public void save(UserModel u) {
		userRepository.save(u);
	}

	// Find user by email
	public UserModel findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Transactional
	public void updatePassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email);
        if (user != null) {
            // Set the new password without encoding
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
}
