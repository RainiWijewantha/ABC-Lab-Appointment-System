package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CredentialsController {

	@RequestMapping("/patientLogin")
	public String example() {
		return "patientLogin";
	}
	
	@RequestMapping("/register")
	public String example1() {
		return "register";
	}
	
	@RequestMapping("/forgotPassword")
	public String example2() {
		return "forgotPassword";
	}
	
	@RequestMapping("/newPassword")
	public String example3() {
		return "newPassword";
	}
	
	@RequestMapping("/header")
	public String example4() {
		return "Header";
	}
	
	@RequestMapping("/footer")
	public String example5() {
		return "Footer";
	}
}
