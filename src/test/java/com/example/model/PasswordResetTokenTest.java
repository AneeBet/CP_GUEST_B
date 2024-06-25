package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class PasswordResetTokenTest {

    @Test
    void testPasswordResetTokenDefaultConstructor() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        assertNull(passwordResetToken.getId());
        assertNull(passwordResetToken.getEmail());
        assertNull(passwordResetToken.getOtp());
        assertNull(passwordResetToken.getExpiryTime());
    }

    @Test
    void testGetAndSetId() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        Long id = 1L;
        passwordResetToken.setId(id);
        assertEquals(id, passwordResetToken.getId());
    }

    @Test
    void testGetAndSetEmail() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        String email = "test@example.com";
        passwordResetToken.setEmail(email);
        assertEquals(email, passwordResetToken.getEmail());
    }

    @Test
    void testGetAndSetOtp() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        String otp = "123456";
        passwordResetToken.setOtp(otp);
        assertEquals(otp, passwordResetToken.getOtp());
    }

    @Test
    void testGetAndSetExpiryTime() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        LocalDateTime expiryTime = LocalDateTime.now();
        passwordResetToken.setExpiryTime(expiryTime);
        assertEquals(expiryTime, passwordResetToken.getExpiryTime());
    }
}
