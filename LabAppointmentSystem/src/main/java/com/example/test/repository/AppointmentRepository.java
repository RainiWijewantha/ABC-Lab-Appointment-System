package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.AppointmentsModel;

public interface AppointmentRepository extends JpaRepository<AppointmentsModel, String>{

}
