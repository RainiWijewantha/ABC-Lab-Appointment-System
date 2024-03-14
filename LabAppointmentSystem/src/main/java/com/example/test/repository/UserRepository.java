package com.example.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
	
	UserModel findByEmail(String email);
	
	boolean existsByEmail(String email);

	// Method to find all users by email
    List<UserModel> findAllByEmail(String email);
    
    UserModel findByFullname(String fullname);
}
