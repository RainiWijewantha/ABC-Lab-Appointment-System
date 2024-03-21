package com.example.test.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.test.model.AppointmentsModel;
import com.example.test.model.PaymentsModel;
import com.example.test.model.TestResultsModel;
import com.example.test.model.UserModel;
import com.example.test.service.AppointmentsService;
import com.example.test.service.EmailService;
import com.example.test.service.OTPService;
import com.example.test.service.PaymentService;
import com.example.test.service.TestResultsService;
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
	
	@Autowired
	private TestResultsService testResultsService;

	@GetMapping("/")
	public String dashboard() {
	
		return "PatientDashboard";
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
			// Add transaction ID and formatted amount to the model to preserve their values
	        model.addAttribute("transactionId", paymentService.generateTransactionId());
	        model.addAttribute("formattedAmount", paymentService.getCurrentAmount());
	        return "Payment";

			//check card number length
		} else  if(paymentsModel.getCardNumber().length() !=16){

			model.addAttribute("message", "Error: please enter correct Card number.");
			// Add transaction ID and formatted amount to the model to preserve their values
	        model.addAttribute("transactionId", paymentService.generateTransactionId());
	        model.addAttribute("formattedAmount", paymentService.getCurrentAmount());
			return "Payment";

			//check vcc number length
		} else if(paymentsModel.getVcc().length() != 3){

			model.addAttribute("message", "Error: please enter correct VCC number");// Add transaction ID and formatted amount to the model to preserve their values
	        model.addAttribute("transactionId", paymentService.generateTransactionId());
	        model.addAttribute("formattedAmount", paymentService.getCurrentAmount());
			return "Payment";

			//Expired Date validation
		} else if(!paymentService.isValidExpirationDate(paymentsModel.getExpierdDate())) {

			model.addAttribute("message", "Error: Invalid expiration date format. Please enter in 'MM/YY' format.");
			// Add transaction ID and formatted amount to the model to preserve their values
	        model.addAttribute("transactionId", paymentService.generateTransactionId());
	        model.addAttribute("formattedAmount", paymentService.getCurrentAmount());
			return "Payment";

		} else {

			// If everything is fine
			// Set the upload date
			paymentsModel.setDate(new Date());
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
			return "redirect:/updatePassword?email=" + email;
		} else {
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
		user.setConfirmpassword(confirmPassword);
		userService.save(user);

		// Redirect to the login page
		return "redirect:/patientLogin";
	}

	@GetMapping("/register")
	public String patientRegister(Model model) {

		// Generate unique ID
		String unique_Id = userService.generateUniqueId();

		model.addAttribute("unique_Id", unique_Id);

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
		
		// Check if email already exists in the database
		else if (userService.emailExists(userModel.getEmail())) {

			model.addAttribute("message", "Error: Email already exists.");
			return "Register";
		}

		else {

			// If everything is fine, proceed with registration
			userService.save(userModel);

			// Redirect to login page 
			return "redirect:/patientLogin";
		}
	}
	
	@GetMapping("/testData")
	public ModelAndView getAllTestData() {
		List<TestResultsModel>list=testResultsService.getAllTestResults();
		return new ModelAndView("Reports","test_results",list);
	}

	@GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPDF(@PathVariable Long id) {
        // Retrieve the TestResultsModel by ID from the database
        TestResultsModel testResult = testResultsService.findById(id);

        // Check if the test result exists
        if (testResult == null) {
            // Return 404 Not Found if the test result does not exist
            return ResponseEntity.notFound().build();
        }

        // Retrieve the PDF content from the test result
        byte[] pdfContent = testResult.getTestDoc();

        // Set the HTTP headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("test_result.pdf").build());

        // Return the PDF content as a ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

}
