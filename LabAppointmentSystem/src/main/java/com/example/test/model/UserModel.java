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
	
	private String fullname;
	
	private String email;
	
	private String address;
	
	private String phonenumber;
	
	private String password;
	
	private String confirmpassword;
	
	private String unique_Id;
	
	
	
	//defauls constructor
	public UserModel() {

	}

	//paramiterized constructor
	public UserModel(Long id, String fullname, String email, String address, String phonenumber, String password,
			String confirmpassword, String unique_Id) {
		super();
		Id = id;
		this.fullname = fullname;
		this.email = email;
		this.address = address;
		this.phonenumber = phonenumber;
		this.password = password;
		this.confirmpassword = confirmpassword;
		this.unique_Id = unique_Id;
	}

	//getters and setters
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
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


	public String getPhonenumber() {
		return phonenumber;
	}


	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getConfirmpassword() {
		return confirmpassword;
	}


	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getUnique_Id() {
		return unique_Id;
	}

	public void setUnique_Id(String unique_Id) {
		this.unique_Id = unique_Id;
	}
	
	
}
