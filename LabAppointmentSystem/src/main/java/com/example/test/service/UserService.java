package com.example.test.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.UserModel;
import com.example.test.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	//validate email
	public boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}

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
	
	// Update user's password
    public void updatePassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
	
}
