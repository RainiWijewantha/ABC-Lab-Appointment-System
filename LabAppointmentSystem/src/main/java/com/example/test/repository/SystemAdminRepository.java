package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.model.SystemAdminModel;

@Repository
public interface SystemAdminRepository extends JpaRepository<SystemAdminModel, Long>{

}
