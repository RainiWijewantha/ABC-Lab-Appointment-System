package com.example.test.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.test.model.UserModel;
import com.example.test.service.PaymentService;
import com.example.test.service.UserService;

@Controller
public class SystemAdminController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserService userService;


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
	public String adminDashboard(Model model) {
		// Get current date
		Date currentDate = new Date();

		// Get daily income
		double dailyIncome = paymentService.getDailyIncome(currentDate);
		model.addAttribute("dailyIncome", dailyIncome);

		// Get monthly income
		double monthlyIncome = paymentService.getMonthlyIncome(currentDate);
		model.addAttribute("monthlyIncome", monthlyIncome);

		// Get annual income
		double annualIncome = paymentService.getAnnualIncome();
		model.addAttribute("annualIncome", annualIncome);

		return "Dashboard";
	}

	@GetMapping("/addUser")
	public String addUser(Model model) {

		// Generate unique ID
		String unique_Id = userService.generateUniqueId();

		model.addAttribute("unique_Id", unique_Id);

		model.addAttribute("userModel", new UserModel());

		return "AddUser";
	}
	
	@PostMapping("/addUser")
	public String AddUser(@ModelAttribute("userModel") UserModel userModel, Model model) {


		// If validation fails, return back to the register page with an error message
		if (userModel == null || userModel.getFullname() == null || userModel.getFullname().isEmpty() ||
				userModel.getEmail() == null || userModel.getEmail().isEmpty() ||
				userModel.getAddress() == null || userModel.getAddress().isEmpty() ||
				userModel.getPhonenumber() == null || userModel.getPhonenumber().isEmpty()) {

			model.addAttribute("message", "Error: All fields are required.");
			return "AddUser";
		}

		//email validation
		else if (!userService.isValidEmail(userModel.getEmail())) {

			model.addAttribute("message", "Error: Please give Valid email.");
			return "AddUser";
		} 
		
		// Check if email already exists in the database
		else if (userService.emailExists(userModel.getEmail())) {

			model.addAttribute("message", "Error: Email already exists.");
			return "AddUser";
		}

		else {

			// If everything is fine, proceed with registration
			userService.save(userModel);

			// Redirect to dashboard
			return "Dashboard";
		}
	}
	
	@GetMapping("/userData")
	public ModelAndView getAllUsers() {
		List<UserModel>list=userService.getAllUsers();
		return new ModelAndView("UserData","user",list);
	}
	
	@PostMapping("/edit")
	public String editUser(@ModelAttribute UserModel userModel) {
		userService.edit(userModel);
		return "redirect:/userData";
	}
	
	@RequestMapping("/editUser/{id}")
	public String edituser(@PathVariable("id") Long id,Model model) {
		UserModel u=userService.getuserById(id);
		model.addAttribute("userModel",u);
		return "UpdateUser";
	}
	
	@RequestMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id")int id) {
		userService.deleteById(id);
		return "redirect:/userData";
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
