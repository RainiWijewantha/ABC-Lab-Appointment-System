package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TechnicianController {

	@GetMapping("/technicianLogin")
	public String technicianLogin(){
		return "TechnicianLogin";
	}

	@PostMapping("/technicianLogin")
	public String technicianTestResults(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
		// Check if the provided username and password match the default credentials
		if ("TechAdmin".equals(username) && "Tech_admin@ABC@123".equals(password)) {
			return "redirect:/testResults";

		} else if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("message", "Error: All fields are required.");
			return "TechnicianLogin";

		} else {
			model.addAttribute("message", "Invalid username or password");
			return "TechnicianLogin";
		}
	}

	@GetMapping("/testResults")
	public String testResults() {
		return "AddTestResults";
	}
}
