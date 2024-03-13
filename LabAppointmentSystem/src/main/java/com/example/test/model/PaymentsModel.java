package com.example.test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Entity
@Table(name = "payment")
public class PaymentsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String transactionId;
	
	private String cardNumber;
	
	private String cardHolderName;
	
	private String vcc;
	
	private String expierdDate;
	
	private double amount;
	
	
	//default constructor
	public PaymentsModel() {
		
	}
	
	//parameterize costructor
	public PaymentsModel(Long id, String transactionId, String cardNumber, String cardHolderName, String vcc, String expierdDate,
			double amount) {
		super();
		this.id = id;
		this.transactionId = transactionId;
		this.cardNumber = cardNumber;
		this.cardHolderName = cardHolderName;
		this.vcc = vcc;
		this.expierdDate = expierdDate;
		this.amount = amount;
	}
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getVcc() {
		return vcc;
	}

	public void setVcc(String vcc) {
		this.vcc = vcc;
	}

	public String getExpierdDate() {
		return expierdDate;
	}

	public void setExpierdDate(String expierdDate) {
		this.expierdDate = expierdDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
	
}
