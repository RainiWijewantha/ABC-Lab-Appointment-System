package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String dashboard() {
		return "redirect:/adminDashboard";
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
}
