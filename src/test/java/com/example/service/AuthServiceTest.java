package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.dto.GuestSignupDTO;
import com.example.model.GuestProfile;
import com.example.model.PasswordResetToken;
import com.example.repository.GuestProfileRepository;
import com.example.repository.PasswordResetTokenRepository;
import com.example.util.Hasher;

public class AuthServiceTest {

    @Mock
    private GuestProfileRepository guestProfileRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private AuthService authService;

    private GuestSignupDTO guestSignupDTO;
    private GuestProfile guestProfile;
    private PasswordResetToken passwordResetToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        guestSignupDTO = new GuestSignupDTO("John Doe", "john.doe@example.com", "password123");
        guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("john.doe@example.com");
        guestProfile.setName("John Doe");
        guestProfile.setPassword(Hasher.hashPassword("password123"));
        passwordResetToken = new PasswordResetToken();
        passwordResetToken.setEmail("john.doe@example.com");
        passwordResetToken.setOtp("123456");
        passwordResetToken.setExpiryTime(LocalDateTime.now().plusMinutes(3));
    }

   

    @Test
    void testSendSignupOtp_EmailAlreadyExists() {
        when(guestProfileRepository.findById(guestSignupDTO.getEmail())).thenReturn(Optional.of(guestProfile));

        ResponseEntity<String> response = authService.sendSignupOtp(guestSignupDTO);

        assertEquals("Email already exists", response.getBody());
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testVerifySignupOtp() {
        Map<String, GuestProfile> signupPendingGuests = new HashMap<>();
        signupPendingGuests.put("123456", guestProfile);
        authService.setSignupPendingGuests(signupPendingGuests);

        boolean result = authService.verifySignupOtp("john.doe@example.com", "123456");

        assertTrue(result);
        verify(guestProfileRepository, times(1)).save(any(GuestProfile.class));
    }

    @Test
    void testVerifySignupOtp_InvalidOtp() {
        boolean result = authService.verifySignupOtp("john.doe@example.com", "invalid");

        assertFalse(result);
        verify(guestProfileRepository, never()).save(any(GuestProfile.class));
    }

    

    @Test
    void testSendPasswordResetOtp_UserNotFound() {
        when(guestProfileRepository.findById("john.doe@example.com")).thenReturn(Optional.empty());

        boolean result = authService.sendPasswordResetOtp("john.doe@example.com");

        assertFalse(result);
        verify(passwordResetTokenRepository, never()).save(any(PasswordResetToken.class));
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testVerifyPasswordResetOtp() {
        when(passwordResetTokenRepository.findByEmailAndOtp("john.doe@example.com", "123456"))
                .thenReturn(Optional.of(passwordResetToken));

        boolean result = authService.verifyPasswordResetOtp("john.doe@example.com", "123456");

        assertTrue(result);
    }

    @Test
    void testVerifyPasswordResetOtp_InvalidOtp() {
        when(passwordResetTokenRepository.findByEmailAndOtp("john.doe@example.com", "invalid")).thenReturn(Optional.empty());

        boolean result = authService.verifyPasswordResetOtp("john.doe@example.com", "invalid");

        assertFalse(result);
    }

    @Test
    void testResetPassword() {
        when(passwordResetTokenRepository.findByEmailAndOtp("john.doe@example.com", "123456"))
                .thenReturn(Optional.of(passwordResetToken));
        when(guestProfileRepository.findById("john.doe@example.com")).thenReturn(Optional.of(guestProfile));

        authService.resetPassword("john.doe@example.com", "123456", "newPassword123");

        verify(guestProfileRepository, times(1)).save(any(GuestProfile.class));
        verify(passwordResetTokenRepository, times(1)).deleteByEmail("john.doe@example.com");
    }

    @Test
    void testResetPassword_InvalidOtp() {
        when(passwordResetTokenRepository.findByEmailAndOtp("john.doe@example.com", "invalid")).thenReturn(Optional.empty());

        authService.resetPassword("john.doe@example.com", "invalid", "newPassword123");

        verify(guestProfileRepository, never()).save(any(GuestProfile.class));
        verify(passwordResetTokenRepository, never()).deleteByEmail("john.doe@example.com");
    }

    

    @Test
    void testGetSignupPendingGuests() {
        Map<String, GuestProfile> signupPendingGuests = new HashMap<>();
        authService.setSignupPendingGuests(signupPendingGuests);
        assertEquals(signupPendingGuests, authService.getSignupPendingGuests());
    }

    @Test
    void testSetSignupPendingGuests() {
        Map<String, GuestProfile> signupPendingGuests = new HashMap<>();
        authService.setSignupPendingGuests(signupPendingGuests);
        assertEquals(signupPendingGuests, authService.getSignupPendingGuests());
    }
}
