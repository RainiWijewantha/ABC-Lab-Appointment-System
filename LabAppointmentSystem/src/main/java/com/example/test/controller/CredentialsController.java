package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CredentialsController {

	@RequestMapping("/patientLogin")
	public String example() {
		return "PatientLogin";
	}
	
	@RequestMapping("/register")
	public String example1() {
		return "Register";
	}
	
	@RequestMapping("/forgotPassword")
	public String example2() {
		return "ForgotPassword";
	}
	
	@RequestMapping("/newPassword")
	public String example3() {
		return "NewPassword";
	}
	
	@RequestMapping("/header")
	public String example4() {
		return "Header";
	}
	
	@RequestMapping("/footer")
	public String example5() {
		return "Footer";
	}
	
	@RequestMapping("/patientDashboard")
	public String example6() {
		return "PatientDashboard";
	}
	
	@RequestMapping("/requestMedicalTest")
	public String example7() {
		return "RequestMedicalTest";
	}
	
	@RequestMapping("/viewAppointmentDetails")
	public String example8() {
		return "ViewAppointmentDetails";
	}
	
	@RequestMapping("/reports")
	public String Reports() {
		return "Reports";
	}
	
	@RequestMapping("/login")
	public String Login() {
		return "Login";
	}
	
	@RequestMapping("/dashboard")
	public String Dashboard() {
		return "Dashboard";
	}
	
	@RequestMapping("/addUser")
	public String AddUser() {
		return "AddUser";
	}
	
	@RequestMapping("/updateUser")
	public String UpdateUser() {
		return "UpdateUser";
	}
	
	@RequestMapping("/deleteUser")
	public String DeleteUser() {
		return "DeleteUser";
	}
	
	@RequestMapping("/message")
	public String Message() {
		return "Message";
	}
	
	@RequestMapping("/pay")
	public String Pay() {
		return "Payment";
	}
}
