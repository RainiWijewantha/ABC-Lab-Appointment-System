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
@Table(name="doctor")
public class DoctorModel {
	
	@Id
	private String Id;
	private String userName = "Doctor";
	private String password = "Doctor@ABC@123";
	
	
	//constructor
	public DoctorModel(String id, String userName, String password) {
		super();
		Id = id;
		this.userName = userName;
		this.password = password;
	}
	
	//getters and setters
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
