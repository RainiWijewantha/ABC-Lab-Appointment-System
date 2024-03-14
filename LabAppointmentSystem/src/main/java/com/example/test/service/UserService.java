package com.example.test.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
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

	// Method to check if email already exists in the database
	public boolean emailExists(String email) {
		// Use UserRepository to check if the email exists
		return userRepository.existsByEmail(email);
	}

	//Authenticate User
	/*public boolean authenticateUser(String email, String password) {
		UserModel user = userRepository.findByEmail(email);
		return user != null && user.getPassword().equals(password);
	}*/

	//Authenticate User
	public boolean authenticateUser(String email, String password) {
		List<UserModel> users = userRepository.findAllByEmail(email);
		for (UserModel user : users) {
			if (user.getPassword().equals(password)) {
				return true; // Return true if password matches for any user with the given email
			}
		}
		return false; // Return false if no user with the given email or password match found
	}

	//Save User
	public void save(UserModel userModel) {

		// Generate a unique transaction ID
		String uniqueId = generateUniqueId();
		// Set the transaction ID
		userModel.setUnique_Id(uniqueId);

		userRepository.save(userModel);
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

	// Generate a unique ID
	public String generateUniqueId() {

		// Create a SimpleDateFormat instance to format the current date and time
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		// Get the current date and time as a formatted string
		String timestamp = dateFormat.format(new Date());

		// Generate a random number as a string
		Random random = new Random();
		int randomNumber = random.nextInt(1000); // Generate a random number between 0 and 999
		String randomString = String.format("%03d", randomNumber); // Pad the number with leading zeros if necessary

		// Combine the timestamp and random number to create the transaction ID
		String unique_Id = "REG-ABC" + timestamp + randomString;

		return unique_Id;
	}

}
