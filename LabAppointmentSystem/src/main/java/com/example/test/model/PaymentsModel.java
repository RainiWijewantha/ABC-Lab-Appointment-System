package com.example.test.model;

import jakarta.persistence.Entity;
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
	private String id;
	
	private String patient_name;
	
	private double amount;
	
	
	//costructor
	public PaymentsModel(String id, String patient_name, double amount) {
		super();
		this.id = id;
		this.patient_name = patient_name;
		this.amount = amount;
	}
	
	//Getters and Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
