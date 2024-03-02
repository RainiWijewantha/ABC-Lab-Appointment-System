package com.example.test.model;

import java.sql.Blob;

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
@Table(name = "test_results")
public class TestResultsModel {

	@Id
	private String id;
	
	private String name;
	
	private Blob test_doc;

	
	//constructor
	public TestResultsModel(String id, String name, Blob test_doc) {
		super();
		this.id = id;
		this.name = name;
		this.test_doc = test_doc;
	}

	//Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getTest_doc() {
		return test_doc;
	}

	public void setTest_doc(Blob test_doc) {
		this.test_doc = test_doc;
	}

}
