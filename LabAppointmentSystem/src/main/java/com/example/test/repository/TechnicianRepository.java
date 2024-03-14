package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.model.TechnicianModel;

@Repository
public interface TechnicianRepository extends JpaRepository<TechnicianModel, String>{

}
