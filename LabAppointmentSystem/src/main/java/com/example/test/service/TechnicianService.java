package com.example.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.repository.TechnicianRepository;

@Service
public class TechnicianService {
	
	@Autowired
	private TechnicianRepository technicianRepository;
}
