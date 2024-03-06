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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String full_name;
	private String email;
	private String address;
	private String phone_number;
	private String password;
	private String comfirm_password;
	
	
	//constructor
	public UserModel(Long id, String full_name, String email, String address, String phone_number, String password, String confirm_password) {
		super();
		Id = id;
		this.full_name = full_name;
		this.email = email;
		this.address = address;
		this.phone_number = phone_number;
		this.password = password;
		this.comfirm_password = confirm_password;
	}

	//getters and setters
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
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


	public String getComfirm_password() {
		return comfirm_password;
	}


	public void setComfirm_password(String comfirm_password) {
		this.comfirm_password = comfirm_password;
	}
	
	
	
	
	
}
