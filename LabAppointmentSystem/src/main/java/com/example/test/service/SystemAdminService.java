package com.example.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.repository.SystemAdminRepository;

@Service
public class SystemAdminService {
	
	@Autowired
	private SystemAdminRepository systemAdminRepository;

}
