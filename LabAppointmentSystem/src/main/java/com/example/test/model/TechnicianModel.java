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
@Table(name="technician")
public class TechnicianModel {
	
	@Id
	private String Id;
	private String userName = "TechAdmin";
	private String password = "Tech_admin@ABC@123";
	
	
	//constructor
	public TechnicianModel(String id, String userName, String password) {
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
