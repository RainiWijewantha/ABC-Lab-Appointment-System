package com.example.test.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.repository.PaymentRepository;
import com.example.test.repository.UserRepository;


@Service
public class SystemAdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentRepository paymentRepository;


	// Method to count the number of users in the system
	public long getUserCount() {
		return userRepository.count();
	}

	// Method to retrieve daily income from the database
	public double getDailyIncome(Date date) {
		return paymentRepository.getDailyIncome(date);
	}

	// Method to retrieve monthly income from the database
	public double getMonthlyIncome(Date date) {
		return paymentRepository.getMonthlyIncome(date);
	}

	// Method to retrieve annual income from the database
	public double getAnnualIncome() {
		// Get the current year
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);

		// Get the start date of the year
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = calendar.getTime();

		// Get the end date of the year
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		Date endDate = calendar.getTime();

		// Fetch payments for the entire year
		return paymentRepository.getAnnualIncome(startDate, endDate);
	}

}




