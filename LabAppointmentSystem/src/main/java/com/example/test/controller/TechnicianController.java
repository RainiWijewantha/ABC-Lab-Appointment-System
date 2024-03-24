package com.example.test.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.test.model.TestResultsModel;
import com.example.test.repository.TestResultsRepository;


@Controller
public class TechnicianController {

	@Autowired
	private TestResultsRepository testResultsRepository;

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
	public String testResults(Model model) {

		model.addAttribute("testResultsModel", new TestResultsModel());
		return "AddTestResults";
	}

	@PostMapping("/testResults")
	public String handleTestResultsForm(@RequestParam("testDocFile") MultipartFile file,
			@ModelAttribute TestResultsModel testResultsModel, Model model) throws IOException {

		// Check if the file is not empty
		if (file.isEmpty()) {
			model.addAttribute("message", "Error: Please select a file to upload.");
			return "AddTestResults";
		}

		// Check if the file content type is PDF
		else if (!file.getContentType().equalsIgnoreCase("application/pdf")) {

			model.addAttribute("message", "Error: Only PDF files are allowed.");
			return "AddTestResults";
		}

		// Check file size
		else if (file.getSize() > DataSize.ofMegabytes(40).toBytes()) { 
			model.addAttribute("message", "Error: File size exceeds the maximum allowed size.");
			return "AddTestResults";
		}

		// Check if file is uploaded
		if (!file.isEmpty()) {
			// Set the file data as byte array
			testResultsModel.setTestDoc(file.getBytes());
		}

		// Set the upload date
		testResultsModel.setUpload_date(new Date());

		// Save the data to database
		testResultsRepository.save(testResultsModel);

		// Redirect to a success page or any other page
		return "redirect:/testResults";
	}


}
