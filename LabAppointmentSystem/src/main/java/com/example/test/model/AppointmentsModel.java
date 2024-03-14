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
@Table(name = "appointments")
public class AppointmentsModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String patient_name;

	private String doctor_name;

	private String test_type;

	private String date;

	//defauls constructor
	public AppointmentsModel() {

	}

	//constructor	
	public AppointmentsModel(Long id, String patient_name, String doctor_name, String test_type, String date) {
		super();
		this.id = id;
		this.patient_name = patient_name;
		this.doctor_name = doctor_name;
		this.test_type = test_type;
		this.date = date;
	}

	//Getters and Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPatient_name() {
		return patient_name;
	}
	
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	
	public String getDoctor_name() {
		return doctor_name;
	}
	
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	
	public String getTest_type() {
		return test_type;
	}
	
	public void setTest_type(String test_type) {
		this.test_type = test_type;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
}