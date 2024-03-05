package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	

}
