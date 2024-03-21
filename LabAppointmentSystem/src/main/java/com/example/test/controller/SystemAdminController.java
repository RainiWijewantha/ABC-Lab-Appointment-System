package com.example.test.controller;

import java.io.ByteArrayOutputStream;
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

import com.example.test.model.AppointmentsModel;
import com.example.test.model.TestResultsModel;
import com.example.test.model.UserModel;
import com.example.test.service.AppointmentsService;
import com.example.test.service.SystemAdminService;
import com.example.test.service.TestResultsService;
import com.example.test.service.UserService;
import com.itextpdf.text.Document;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;


@Controller
public class SystemAdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentsService appointmentsService;

	@Autowired
	private TestResultsService testResultsService;

	@Autowired
	private SystemAdminService systemAdminService;


	@GetMapping("/adminLogin")
	public String adminLogin() {
		return "Login";
	}

	@PostMapping("/adminLogin")
	public String dashboard(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {

		// Check if the provided username and password match the default credentials
		if ("SysAdmin".equals(username) && "SysAdmin-ABC@123".equals(password)) {
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
		double dailyIncome = systemAdminService.getDailyIncome(currentDate);
		String formattedDailyIncome = String.format("%.2f", dailyIncome);
		model.addAttribute("dailyIncome", formattedDailyIncome);

		// Get monthly income
		double monthlyIncome = systemAdminService.getMonthlyIncome(currentDate);
		String formattedMonthlyIncome = String.format("%.2f", monthlyIncome);
		model.addAttribute("monthlyIncome", formattedMonthlyIncome);

		// Get annual income
		double annualIncome = systemAdminService.getAnnualIncome();
		String formattedAnnualIncome = String.format("%.2f", annualIncome);
		model.addAttribute("annualIncome", formattedAnnualIncome);

		// Get user count
		long userCount = systemAdminService.getUserCount();
		model.addAttribute("userCount", userCount);

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
			return "redirect:/adminDashboard";
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
	public String deleteUser(@PathVariable("id")Long id) {
		userService.deleteById(id);
		return "redirect:/userData";
	}

	@GetMapping("/appointmentSchedule")
	public ModelAndView getAllAppointments() {
		List<AppointmentsModel>list=appointmentsService.getAllAppointments();
		return new ModelAndView("AppointmentScheduleReport","appointments",list);
	}

	@GetMapping("/generateAppointmentReport")
	public void generateAppointmentReport(HttpServletResponse response) {
		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"appointment_report.pdf\"");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
			PdfWriter.getInstance(document, baos);
			document.open();

			List<AppointmentsModel> appointments = appointmentsService.getAllAppointments();

			// Create a table with 5 columns
			PdfPTable table = new PdfPTable(5);

			// Add table header
			table.addCell("Id");
			table.addCell("Patient Name");
			table.addCell("Test Type");
			table.addCell("Doctor Name");
			table.addCell("Date");
			for (AppointmentsModel appointment : appointments) {
				table.addCell(String.valueOf(appointment.getId()));
				table.addCell(appointment.getPatient_name());
				table.addCell(appointment.getTest_type());
				table.addCell(appointment.getDoctor_name());
				table.addCell(appointment.getDate().toString());
			}

			// Add the table to the document
			document.add(table);


			document.close();

			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/testResultsReport")
	public ModelAndView getAllTestResults() {
		List<TestResultsModel>list=testResultsService.getAllTestResults();
		return new ModelAndView("TestResultsReport","test_results",list);
	}

	@GetMapping("/generateTestResultsReport")
	public void generateTestResultReport(HttpServletResponse response) {

		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"test_results.pdf\"");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, baos);
			document.open();

			List<TestResultsModel> testResults = testResultsService.getAllTestResults();

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);

			table.addCell("Id");
			table.addCell("Patient Name");
			table.addCell("Test Type");
			table.addCell("Test Date");
			//table.addCell("Test Result");

			for (TestResultsModel test : testResults) {

				table.addCell(String.valueOf(test.getId()));
				table.addCell(test.getPatient_name());
				table.addCell(test.getTest_date());
				table.addCell(test.getTest_type());
			}

			document.add(table);

			document.close();

			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
