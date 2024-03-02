package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.Getter;

@Controller
public class DoctorController {

	@GetMapping("/doctorLogin")
	public String doctorLogin(){
		return "DoctorLogin";
	}
	
	@PostMapping("/doctorLogin")
	public String doctorTestResults() {
		return "redirect:/testResults";
	}
	
	@GetMapping("/testResults")
    public String testResults() {
        return "TestResults";
    }
}
