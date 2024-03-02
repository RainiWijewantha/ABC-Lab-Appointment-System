package com.example.test.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Entity
@Table(name="sys_admin")
public class SystemAdminModel {
	
	@Id
	private String Id;
	private String Username = "SysAdmin";
	private String Password = "Admin@123";
	
	
	//constructor
	public SystemAdminModel(String id, String username, String password) {
		super();
		Id = id;
		Username = username;
		Password = password;
	}
	
	//getters and setters
	public String getId() {
		return Id;
	}
	
	public void setId(String id) {
		Id = id;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
