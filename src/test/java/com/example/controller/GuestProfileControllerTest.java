package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.dto.GuestApplicationDTO;
import com.example.dto.GuestProfileTrackApplicationDTO;
import com.example.dto.GuestSignupDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.GuestProfile;
import com.example.service.AuthService;
import com.example.service.GuestProfileService;



public class GuestProfileControllerTest {

    @Mock
    private GuestProfileService guestProfileService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private GuestProfileController guestProfileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetGuestById_Success() {
        GuestProfile guestProfile = new GuestProfile();
        when(guestProfileService.getGuestById("test@example.com")).thenReturn(Optional.of(guestProfile));

        ResponseEntity<GuestProfile> response = guestProfileController.getGuestById("test@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guestProfile, response.getBody());
    }

    @Test
    public void testGetGuestById_NotFound() {
        when(guestProfileService.getGuestById("test@example.com")).thenReturn(Optional.empty());

        ResponseEntity<GuestProfile> response = guestProfileController.getGuestById("test@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateGuest_Success() throws ResourceNotFoundException {
        GuestProfile guestProfile = new GuestProfile();
        GuestApplicationDTO guestApplicationDTO = new GuestApplicationDTO();
        when(guestProfileService.updateGuest(eq("test@example.com"), any(GuestApplicationDTO.class))).thenReturn(guestProfile);

        ResponseEntity<GuestProfile> response = guestProfileController.updateGuest("test@example.com", guestApplicationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guestProfile, response.getBody());
    }

    @Test
    public void testTrackApplication_Success() {
        GuestProfileTrackApplicationDTO trackApplicationDTO = new GuestProfileTrackApplicationDTO();
        when(guestProfileService.trackApplication("test@example.com")).thenReturn(trackApplicationDTO);

        ResponseEntity<GuestProfileTrackApplicationDTO> response = guestProfileController.trackApplication("test@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trackApplicationDTO, response.getBody());
    }

    @Test
    public void testTrackApplication_NotFound() {
        when(guestProfileService.trackApplication("test@example.com")).thenReturn(null);

        ResponseEntity<GuestProfileTrackApplicationDTO> response = guestProfileController.trackApplication("test@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testInitiateSignup() {
        GuestSignupDTO guestSignupDTO = new GuestSignupDTO();
        when(authService.sendSignupOtp(guestSignupDTO)).thenReturn(ResponseEntity.ok("OTP sent"));

        ResponseEntity<String> response = guestProfileController.initiateSignup(guestSignupDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OTP sent", response.getBody());
    }
    
    @Test
    public void testVerifySignupOtp_Success() {
       
        when(authService.verifySignupOtp("test@example.com", "123456")).thenReturn(true);
        boolean result = guestProfileController.verifySignupOtp(Map.of("email", "test@example.com", "otp", "123456"));
        assertTrue(result);
    }


    @Test
    public void testVerifySignupOtp_Failure() {

        when(authService.verifySignupOtp("test@example.com", "123456")).thenReturn(false);
        boolean result = guestProfileController.verifySignupOtp(Map.of("email", "test@example.com", "otp", "123456"));

        assertFalse(result);
    }


    @Test
    public void testLoginGuest() {
        when(guestProfileService.loginGuest("test@example.com", "password")).thenReturn(ResponseEntity.ok("Login successful"));

        ResponseEntity<String> response = guestProfileController.loginGuest(Map.of("email", "test@example.com", "password", "password"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    public void testForgotPassword_Success() {
        when(authService.sendPasswordResetOtp("test@example.com")).thenReturn(true);

        boolean response = guestProfileController.forgotPassword(Map.of("email", "test@example.com"));

        assertEquals(true, response);
    }

    @Test
    public void testForgotPassword_Failure() {
        when(authService.sendPasswordResetOtp("test@example.com")).thenReturn(false);

        boolean response = guestProfileController.forgotPassword(Map.of("email", "test@example.com"));

        assertEquals(false, response);
    }

    @Test
    public void testVerifyPasswordResetOtp() throws BadRequestException {
        when(authService.verifyPasswordResetOtp("test@example.com", "123456")).thenReturn(true);
 
        boolean response = guestProfileController.verifyPasswordResetOtp(Map.of("email", "test@example.com", "otp", "123456"));
 
        assertTrue(response);
    
    }

    @Test
    public void testResetPassword() {
        guestProfileController.resetPassword(Map.of("email", "test@example.com", "otp", "123456", "password", "newpassword"));

        // No assertions as the method returns void, but you can verify interactions with mocks if needed.
        // verify(authService).resetPassword("test@example.com", "123456", "newpassword");
    }
}
