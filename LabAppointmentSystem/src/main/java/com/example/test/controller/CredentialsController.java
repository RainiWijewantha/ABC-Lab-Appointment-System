package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CredentialsController {

	@RequestMapping("/credentials")
	public String example() {
		return "example";
	}
}
