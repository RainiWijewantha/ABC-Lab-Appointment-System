package com.example.test.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.PaymentsModel;
import com.example.test.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	//Save Payments
	public void save(PaymentsModel paymentsModel) {
		
		// Generate a unique transaction ID
        String transactionId = generateTransactionId();
        // Set the transaction ID
        paymentsModel.setTransactionId(transactionId);
        // Save payment details to the database
		paymentRepository.save(paymentsModel);
	}
	
	// Generate a unique transaction ID
    public String generateTransactionId() {

    	// Create a SimpleDateFormat instance to format the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        // Get the current date and time as a formatted string
        String timestamp = dateFormat.format(new Date());

        // Generate a random number as a string
        Random random = new Random();
        int randomNumber = random.nextInt(1000); // Generate a random number between 0 and 999
        String randomString = String.format("%03d", randomNumber); // Pad the number with leading zeros if necessary

        // Combine the timestamp and random number to create the transaction ID
        String transactionId = "TXN" + timestamp + randomString;
        
        return transactionId;
    }

	//expired date validation
	public boolean isValidExpirationDate(String expirationDate) {
		// Check if the expiration date matches the format 'MM/YYYY'
		// Using regular expression to validate the format
		String regex = "^(0[1-9]|1[0-2])/\\d{2}$";
		return expirationDate.matches(regex);
	}

	// Method to fetch the current amount 
	public double getCurrentAmount() {

		double defaultAmount = 500.0;
		Random rand = new Random();
	    double currentAmount = defaultAmount + rand.nextDouble() * 150.0;
	    return currentAmount;

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
