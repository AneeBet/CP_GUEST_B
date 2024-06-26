package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.dto.GuestApplicationDTO;
import com.example.dto.GuestProfileTrackApplicationDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.ApplicationStatus;
import com.example.model.CreditCard;
import com.example.model.GuestProfile;
import com.example.repository.CreditCardRepository;
import com.example.repository.GuestProfileRepository;
import com.example.util.Hasher;
import com.example.util.KeyGenerator;

public class GuestProfileServiceTest {

    @Mock
    private GuestProfileRepository guestProfileRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private KeyGenerator generator;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private GuestProfileService guestProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGuestById() {
        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");

        when(guestProfileRepository.findById("test@example.com")).thenReturn(Optional.of(guestProfile));

        Optional<GuestProfile> result = guestProfileService.getGuestById("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getGuestEmail());
    }

    @Test
    public void testCreateGuest() {
        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");

        when(guestProfileRepository.save(any(GuestProfile.class))).thenReturn(guestProfile);

        GuestProfile result = guestProfileService.createGuest(guestProfile);
        assertEquals("test@example.com", result.getGuestEmail());
    }

    @Test
    public void testSave() {
        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");

        when(guestProfileRepository.save(any(GuestProfile.class))).thenReturn(guestProfile);

        GuestProfile result = guestProfileService.save(guestProfile);
        assertEquals("test@example.com", result.getGuestEmail());
    }

    @Test
    public void testGetGuestByPanNumber() {
        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");

        when(guestProfileRepository.findById("test@example.com")).thenReturn(Optional.of(guestProfile));

        Optional<GuestProfile> result = guestProfileService.getGuestByPanNumber("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getGuestEmail());
    }

    @Test
    public void testUpdateGuest() throws ResourceNotFoundException {
        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");

        GuestApplicationDTO guestDetails = new GuestApplicationDTO();
        guestDetails.setAadhaarNumber(123456789012L);
        guestDetails.setCardType("testCard");

        when(guestProfileRepository.findById("test@example.com")).thenReturn(Optional.of(guestProfile));
        when(creditCardRepository.findById("testCard")).thenReturn(Optional.of(new CreditCard()));
        when(generator.generateUniqueApplicationId()).thenReturn(123L);
        when(guestProfileRepository.save(any(GuestProfile.class))).thenReturn(guestProfile);

    }

    @Test
    public void testUpdateGuestNotFound() {
        GuestApplicationDTO guestDetails = new GuestApplicationDTO();
        when(guestProfileRepository.findById("test@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            guestProfileService.updateGuest("test@example.com", guestDetails);
        });
    }

    @Test
    public void testLoginGuest() {
        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");

        when(guestProfileRepository.findByGuestEmailAndPassword("test@example.com", Hasher.hashPassword("password")))
                .thenReturn(Optional.of(guestProfile));

        ResponseEntity<String> response = guestProfileService.loginGuest("test@example.com", "password");
        assertEquals("Guest logged in successfully!", response.getBody());
    }

    @Test
    public void testLoginGuestInvalid() {
        when(guestProfileRepository.findByGuestEmailAndPassword("test@example.com", Hasher.hashPassword("password")))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = guestProfileService.loginGuest("test@example.com", "password");
        assertEquals("Invalid email or password", response.getBody());
    }

    @Test
    public void testTrackApplication() {
        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");
        CreditCard creditCard = new CreditCard();
        creditCard.setCardType("testCard");
        guestProfile.setCreditCard(creditCard);
        guestProfile.setApplicationId(123L);
        guestProfile.setApplicationStatus(ApplicationStatus.APPROVED);

        when(guestProfileRepository.findById("test@example.com")).thenReturn(Optional.of(guestProfile));

        GuestProfileTrackApplicationDTO result = guestProfileService.trackApplication("test@example.com");
        assertNotNull(result);
        assertEquals("testCard", result.getCardType());
        assertEquals(123L, result.getApplicationId());
    }

    @Test
    public void testTrackApplicationNotFound() {
        when(guestProfileRepository.findById("test@example.com")).thenReturn(Optional.empty());

        GuestProfileTrackApplicationDTO result = guestProfileService.trackApplication("test@example.com");
        assertNull(result);
    }
}
