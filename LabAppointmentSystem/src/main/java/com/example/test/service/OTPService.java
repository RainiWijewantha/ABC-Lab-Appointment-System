package com.example.test.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OTPService {
		
	private Map<String, String> otpMap = new HashMap<>();
    private Random random = new Random();

    public String generateOTP() {
        String otp = String.format("%04d", random.nextInt(10000));
        return otp;
    }

    public void saveOTP(String email, String otp) {
        otpMap.put(email, otp);
    }

    public boolean verifyOTP(String email, String otp) {
        if (otpMap.containsKey(email) && otpMap.get(email).equals(otp)) {
            otpMap.remove(email);
            return true;
        }
        return false;
    }
    
}
