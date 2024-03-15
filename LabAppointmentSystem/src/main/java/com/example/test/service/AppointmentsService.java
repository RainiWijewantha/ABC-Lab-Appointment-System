package com.example.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.AppointmentsModel;
import com.example.test.repository.AppointmentsRepository;

@Service
public class AppointmentsService {
	
	@Autowired
	private AppointmentsRepository appointmentRepository;

	//Save User
	public void save(AppointmentsModel appointmentsModel) {
		appointmentRepository.save(appointmentsModel);
	}

	public List<AppointmentsModel> getAllAppointments() {
		return appointmentRepository.findAll();
	}

}
