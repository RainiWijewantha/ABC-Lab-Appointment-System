package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

	@GetMapping("/register")
	public String patientRegister() {
		return "Register";
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
	public String afterLogin() {
		return "redirect:/payment";
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

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userModel") UserModel userModel, Model model) {

		// If validation fails, return back to the register page with an error message
		if (userModel == null || userModel.getFull_name() == null || userModel.getFull_name().isEmpty() ||
				userModel.getEmail() == null || userModel.getEmail().isEmpty() ||
				userModel.getAddress() == null || userModel.getAddress().isEmpty() ||
				userModel.getPhone_number() == null || userModel.getPhone_number().isEmpty() ||
				userModel.getPassword() == null || userModel.getPassword().isEmpty() ||
				userModel.getComfirm_password() == null || userModel.getComfirm_password().isEmpty()) {
			model.addAttribute("message", "Error: All fields are required.");
			return "Register";
		}

		// Check if password and confirm password match
		else if (!userModel.getPassword().equals(userModel.getComfirm_password())) {
			model.addAttribute("message", "Error: Passwords do not match.");
			return "Register";
		}
		else {
			// If everything is fine, proceed with registration
			userService.save(userModel);

			// Optionally, you can add a success message
			model.addAttribute("message", "Success: Registration successful!");

			// Redirect to login page or any other appropriate page
			return "redirect:/patientLogin";
		}


	}



}
