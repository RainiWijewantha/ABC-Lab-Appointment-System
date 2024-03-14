package com.example.test.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.test.model.PaymentsModel;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine templateEngine;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendReceiptEmail(PaymentsModel paymentsModel) {

		MimeMessage message = emailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(paymentsModel.getEmail());
			helper.setSubject("Payment Receipt");
			Context context = new Context();
			context.setVariable("transactionId", paymentsModel.getTransactionId());
			
			LocalDate currentDate = LocalDate.now();
			// Format the date
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = currentDate.format(formatter);

	        // Pass the formatted date to the Thymeleaf template
	        context.setVariable("date", formattedDate);
	        
			//context.setVariable("date", paymentsModel.getDate());
			context.setVariable("amount", paymentsModel.getAmount());
			String htmlContent = templateEngine.process("Receipt", context);
			helper.setText(htmlContent, true);
			emailSender.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
