package com.example.test.model;

import java.util.Date;

import jakarta.persistence.Column;
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
@Table(name = "test_results")
public class TestResultsModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String patient_name;

	@Column(columnDefinition = "LONGBLOB")
	private byte[] testDoc; // Store file data as byte array

	private Date upload_date;

	private String test_date;

	private String test_type;

	private String unique_id;

	//default constructor
	public TestResultsModel() {

	}

	//parameterized constructor
	public TestResultsModel(Long id, String patient_name, byte[] testDoc, Date upload_date, String test_date,
			String test_type, String unique_id) {
		super();
		this.id = id;
		this.patient_name = patient_name;
		this.testDoc = testDoc;
		this.upload_date = upload_date;
		this.test_date = test_date;
		this.test_type = test_type;
		this.unique_id= unique_id;
	}

	// Getters and Setters
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

	public byte[] getTestDoc() {
		return testDoc;
	}

	public void setTestDoc(byte[] testDoc) {
		this.testDoc = testDoc;
	}

	public Date getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}

	public String getTest_date() {
		return test_date;
	}

	public void setTest_date(String test_date) {
		this.test_date = test_date;
	}

	public String getTest_type() {
		return test_type;
	}

	public void setTest_type(String test_type) {
		this.test_type = test_type;
	}

	public String getUnique_id() {
		return unique_id;
	}

	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}
	
	

}
