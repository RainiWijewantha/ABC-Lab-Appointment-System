package com.example.test.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.model.UserModel;
import com.example.test.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String dashboard() {
		return "PatientDashboard";
	}

	@GetMapping("/requestMedicalTest")
	public String requestMedicalTest() {
		return "RequestMedicalTest";
	}

	@GetMapping("/viewAppointmentDetails")
	public String viewAppointmentDetails() {
		return "ViewAppointmentDetails";
	}

	@GetMapping("/reports")
	public String reports() {
		return "Reports";
	}

	@GetMapping("/aboutUs") 
	public String aboutUs() {
		return "AboutUs"; 
	}

	@GetMapping("/contactUs") 
	public String contactUs() {
		return "ContactUs"; 
	}

	@GetMapping("/message")
	public String message() {
		return "Message";
	}

	@PostMapping("/next")
	public String displayMessage() {
		return "redirect:/message";
	}

	@GetMapping("/patientLogin")
	public String patientLogin() {
		return "PatientLogin";
	}

	@GetMapping("/payment")
	public String payment() {
		return "Payment";
	}

	@PostMapping("/afterLogin")
	public String afterLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {

		if (email.isEmpty() || password.isEmpty()) {
			
	        model.addAttribute("message", "Error: Email and password are required.");
	        return "PatientLogin";
	        
	    } else if (userService.authenticateUser(email, password)) {
			// Authentication successful, redirect to dashboard or any other page
			return "redirect:/";

		} else {
			// Authentication failed, display error message
			model.addAttribute("message", "Invalid email or password");
			return "PatientLogin";
		}
	}

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "ForgotPassword";
	}

	@PostMapping("/forgotPassword")
	public String processForgotPassword() {
		return "redirect:/newPassword";
	}

	@GetMapping("/newPassword")
	public String newPassword() {
		return "NewPassword";
	}

	@PostMapping("/newPassword") 
	public String processNewPassword() {
		return "redirect:/patientLogin"; 
	}

	@GetMapping("/register")
	public String patientRegister(Model model) {
		model.addAttribute("userModel", new UserModel());
		return "Register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userModel") UserModel userModel, Model model) {


		// If validation fails, return back to the register page with an error message
		if (userModel == null || userModel.getFullname() == null || userModel.getFullname().isEmpty() ||
				userModel.getEmail() == null || userModel.getEmail().isEmpty() ||
				userModel.getAddress() == null || userModel.getAddress().isEmpty() ||
				userModel.getPhonenumber() == null || userModel.getPhonenumber().isEmpty() ||
				userModel.getPassword() == null || userModel.getPassword().isEmpty() ||
				userModel.getConfirmpassword() == null || userModel.getConfirmpassword().isEmpty()) {

			model.addAttribute("message", "Error: All fields are required.");
			return "Register";
		}

		// Check if password and confirm password match
		else if (!userModel.getPassword().equals(userModel.getConfirmpassword())) {

			model.addAttribute("message", "Error: Passwords do not match.");
			return "Register";
		}

		//email validation
		else if (!isValidEmail(userModel.getEmail())) {

			model.addAttribute("message", "Error: Please give Valid email.");
			return "Register";
		}

		else {
			// If everything is fine, proceed with registration
			userService.save(userModel);

			// success message
			model.addAttribute("message", "Success: Registration successful!");

			// Redirect to login page 
			return "redirect:/patientLogin";
		}
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}



}
