package com.example.test.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Entity
@Table(name="user")
public class UserModel {
	
	@Id
	private String Id;
	
	private String full_name;
	private String email;
	private String address;
	private String phone_number;
	private String password;
	
	
	//constructor
	public UserModel(String id, String full_name, String email, String address, String phone_number, String password) {
		super();
		Id = id;
		this.full_name = full_name;
		this.email = email;
		this.address = address;
		this.phone_number = phone_number;
		this.password = password;
	}
	
	//getters and setters
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
