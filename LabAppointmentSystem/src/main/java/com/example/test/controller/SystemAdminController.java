package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.model.SystemAdminModel;
import com.example.test.service.SystemAdminService;

@Controller
public class SystemAdminController {
	
	@Autowired
	private SystemAdminService systemAdminService;
	
	
	@GetMapping("/adminLogin")
	public String adminLogin() {
		return "Login";
	}
	
	@PostMapping("/adminLogin")
	public String dashboard(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
		
		// Check if the provided username and password match the default credentials
		if ("SysAdmin".equals(username) && "Admin@123".equals(password)) {
            return "redirect:/adminDashboard";
            
        } else if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("message", "Error: All fields are required.");
            return "Login";
            
        } else {
            model.addAttribute("message", "Invalid username or password");
            return "Login";
        }
	}
	
	@GetMapping("/adminDashboard")
	public String adminDashboard() {
		return "Dashboard";
	}
	
	@GetMapping("/addUser")
	public String addUser() {
		return "AddUser";
	}
	
	@GetMapping("/userData")
	public String userData() {
		return "UserData";
	}
	
	@GetMapping("/addTestResults")
	public String addTestResults() {
		return "AddTestResults";
	}
	
	@GetMapping("/appointmentSchedule")
	public String appointmentSchedule() {
		return "AppointmentSchedule";
	}
}
