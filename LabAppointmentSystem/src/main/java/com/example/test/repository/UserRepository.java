package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
	
	UserModel findByEmail(String email);

}
