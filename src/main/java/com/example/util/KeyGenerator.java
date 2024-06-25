package com.example.util;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.repository.GuestProfileRepository;

@Component
public class KeyGenerator {

   
    
    @Autowired
    private GuestProfileRepository guestRepository;

    public Long generateUniqueApplicationId() {
        Long applicationId;
        SecureRandom random = new SecureRandom();
        do {
            applicationId = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        } while (guestRepository.existsByApplicationId(applicationId));
        return applicationId;
    }

    
}
