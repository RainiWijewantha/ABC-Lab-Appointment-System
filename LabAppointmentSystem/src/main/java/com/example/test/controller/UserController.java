package com.example.test.controller;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.model.AppointmentsModel;
import com.example.test.model.PaymentsModel;
import com.example.test.model.UserModel;
import com.example.test.service.AppointmentsService;
import com.example.test.service.EmailService;
import com.example.test.service.OTPService;
import com.example.test.service.PaymentService;
import com.example.test.service.UserService;


@Controller
public class UserController {

	@Autowired
	private AppointmentsService appointmentsService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OTPService otpService;
	
	@Autowired
	private PaymentService paymentService;

	@GetMapping("/")
	public String dashboard() {
		return "PatientDashboard";
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

	@GetMapping("/message")
	public String message() {
		return "Message";
	}
	
	@GetMapping("/requestMedicalTest")
	public String requestMedicalTest(Model model) {
		model.addAttribute("appointmentsModel", new AppointmentsModel());
		return "RequestMedicalTest";
	}

	@PostMapping("/next")
	public String saveAppointments(@ModelAttribute("appointmentsModel") AppointmentsModel appointmentsModel, Model model) {

		// If validation fails, return back to the register page with an error message
		if (appointmentsModel == null || appointmentsModel.getPatient_name() == null || appointmentsModel.getPatient_name().isEmpty() ||
				appointmentsModel.getDoctor_name() == null || appointmentsModel.getDoctor_name().isEmpty() ||
				appointmentsModel.getTest_type() == null || appointmentsModel.getTest_type().isEmpty() ||
				appointmentsModel.getDate() == null) {

			model.addAttribute("message", "Error: All fields are required.");
			return "RequestMedicalTest";

		} else {

			// If everything is fine, proceed with registration
			appointmentsService.save(appointmentsModel);

			// success message
			model.addAttribute("message", "Success: Registration successful!");

			// Redirect to login page 
			return "redirect:/message";
		}

	}

	@GetMapping("/patientLogin")
	public String patientLogin() {
		return "PatientLogin";
	}

	@GetMapping("/payment")
	public String payment(Model model) {
		
		// Generate transaction ID
	    String transactionId = paymentService.generateTransactionId();
		
		double currentAmount = paymentService.getCurrentAmount();
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedAmount = df.format(currentAmount);
        
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("formattedAmount", formattedAmount);
        
        model.addAttribute("paymentsModel", new PaymentsModel());
        return "Payment";
	}
	
	@PostMapping("/payment")
	public String doPayments(@ModelAttribute("paymentsModel") PaymentsModel paymentsModel, Model model) {


		// If validation fails, return back to the payment page with an error message
		if (paymentsModel == null || paymentsModel.getCardNumber() == null || paymentsModel.getCardNumber().isEmpty() ||
				paymentsModel.getCardHolderName() == null || paymentsModel.getCardHolderName().isEmpty() ||
				paymentsModel.getVcc() == null || paymentsModel.getVcc().isEmpty() ||
				paymentsModel.getExpierdDate() == null || paymentsModel.getExpierdDate().isEmpty()) {

			model.addAttribute("message", "Error: All fields are required.");
			return "Payment";
		
		//check card number length
		} else  if(paymentsModel.getCardNumber().length() !=16){
			
			model.addAttribute("message", "Error: please enter correct Card number.");
			return "Payment";
		
		//check vcc number length
		} else if(paymentsModel.getVcc().length() != 3){
			
			model.addAttribute("message", "Error: please enter correct VCC number");
			return "Payment";
			
		//Expired Date validation
		} else if(!paymentService.isValidExpirationDate(paymentsModel.getExpierdDate())) {
			
			model.addAttribute("message", "Error: Invalid expiration date format. Please enter in 'MM/YY' format.");
			return "Payment";
			
		} else {
		
			// If everything is fine
			paymentService.save(paymentsModel);
			emailService.sendReceiptEmail(paymentsModel);

			// Redirect to dashboard page 
			return "redirect:/";
		}
	}


	@PostMapping("/afterLogin")
	public String afterLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {

		if (email.isEmpty() || password.isEmpty()) {

			model.addAttribute("message", "Error: Email and password are required.");
			return "PatientLogin";

		} else if (userService.authenticateUser(email, password)) {
			// Authentication successful, redirect to dashboard or any other page
			return "redirect:/payment";

		} else {
			// Authentication failed, display error message
			model.addAttribute("message", "Invalid email or password");
			return "PatientLogin";
		}
	}

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "ForgotPassword";
	}

	@PostMapping("/forgotPassword")
	public String processForgotPassword(@RequestParam("email") String email, Model model) {

		// Check if email field is empty
		if (email == null || email.isEmpty()) {
			model.addAttribute("message", "Error: Email field required.");
			return "ForgotPassword";
		}

		// Validate email format
		else if (!userService.isValidEmail(email)) {
			model.addAttribute("message", "Error: Please provide a valid email address.");
			return "ForgotPassword";
		}

		// Check if email exists in the database
		else if (userService.emailExists(email)) {

			// Generate and save OTP
			String otp = otpService.generateOTP();
			otpService.saveOTP(email, otp);

			// Send OTP via email
			String subject = "Password Reset OTP";
			String message = "Your OTP for password reset is: " + otp;
			emailService.sendSimpleMessage(email, subject, message);

			// Redirect to OTP verification page
			return "redirect:/verifyOTPPage?email=" + email;
			
		} else {

			model.addAttribute("message", "Email not found. Please enter a valid email address.");
			return "ForgotPassword";
		}
	}

	@GetMapping("/verifyOTPPage")
	public String showVerifyOTPPage(@RequestParam("email") String email, Model model) {
		model.addAttribute("email", email);
		return "VerifyOTP";
	}

	@PostMapping("/verifyOTP")
	public String verifyOTP(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
		if (otpService.verifyOTP(email, otp)) {
			// OTP verification successful, redirect to new password page
			return "redirect:/updatePassword";
		} else {
			model.addAttribute("email", email);
			model.addAttribute("message", "Invalid OTP. Please try again.");
			return "VerifyOTP";
		}
	}

	@GetMapping("/updatePassword")
	public String showUpdatePasswordForm(@RequestParam("email") String email, Model model) {

		model.addAttribute("email", email);
		return "NewPassword";
	}

	@PostMapping("/updatePassword")
	public String updatePasswsord(@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("email") String email, Model model) {

		if (!password.equals(confirmPassword)) {
			model.addAttribute("email", email);
			model.addAttribute("message", "Passwords do not match. Please try again.");
			return "NewPassword";
		}

		UserModel user = userService.findByEmail(email);
		if (user == null) {
			// Handle error: User not found
			return "redirect:/forgotPassword";
		}

		// Update the password for the user
		user.setPassword(password);
		userService.save(user);

		// Redirect to the login page
		return "redirect:/patientLogin";
	}

	@GetMapping("/register")
	public String patientRegister(Model model) {
		model.addAttribute("userModel", new UserModel());
		return "Register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userModel") UserModel userModel, Model model) {


		// If validation fails, return back to the register page with an error message
		if (userModel == null || userModel.getFullname() == null || userModel.getFullname().isEmpty() ||
				userModel.getEmail() == null || userModel.getEmail().isEmpty() ||
				userModel.getAddress() == null || userModel.getAddress().isEmpty() ||
				userModel.getPhonenumber() == null || userModel.getPhonenumber().isEmpty() ||
				userModel.getPassword() == null || userModel.getPassword().isEmpty() ||
				userModel.getConfirmpassword() == null || userModel.getConfirmpassword().isEmpty()) {

			model.addAttribute("message", "Error: All fields are required.");
			return "Register";
		}

		// Check if password and confirm password match
		else if (!userModel.getPassword().equals(userModel.getConfirmpassword())) {

			model.addAttribute("message", "Error: Passwords do not match.");
			return "Register";
		}

		//email validation
		else if (!userService.isValidEmail(userModel.getEmail())) {

			model.addAttribute("message", "Error: Please give Valid email.");
			return "Register";
		}

		else {

			// If everything is fine, proceed with registration
			userService.save(userModel);

			// success message
			model.addAttribute("message", "Success: Registration successful!");

			// Redirect to login page 
			return "redirect:/patientLogin";
		}
	}

	


}
