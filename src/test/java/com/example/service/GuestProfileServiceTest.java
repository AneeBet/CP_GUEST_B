package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.dto.GuestApplicationDTO;
import com.example.dto.GuestProfileTrackApplicationDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.ApplicationStatus;
import com.example.model.CreditCard;
import com.example.model.GuestProfile;
import com.example.repository.CreditCardRepository;
import com.example.repository.GuestProfileRepository;
import com.example.util.KeyGenerator;

@ExtendWith(MockitoExtension.class)
public class GuestProfileServiceTest {
	@Mock
	private CreditCardRepository creditCardRepository;

	@Mock
	private GuestProfileRepository guestProfileRepository;

	@Mock
	private JavaMailSender mailSender; // Mock JavaMailSender

	@Mock
	private KeyGenerator keyGenerator; // Ensure this mock is declared

	@InjectMocks
	private GuestProfileService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testUpdateGuest_Successful() throws ResourceNotFoundException {
	    // Arrange
	    String guestId = "existingId";
	    GuestProfile existingGuestProfile = new GuestProfile();
	    GuestApplicationDTO dto = new GuestApplicationDTO(); // Set appropriate parameters

	    when(guestProfileRepository.findById(guestId)).thenReturn(Optional.of(existingGuestProfile));
	    when(keyGenerator.generateUniqueApplicationId()).thenReturn(12345L);
	    when(guestProfileRepository.save(any(GuestProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

	    // Mock behavior for mailSender
	    doNothing().when(mailSender).send(any(SimpleMailMessage.class));

	    // Act
	    GuestProfile updatedGuestProfile = service.updateGuest(guestId, dto);

	    // Assert
	    assertNotNull(updatedGuestProfile);
	    verify(mailSender).send(any(SimpleMailMessage.class));  // Ensure mailSender.send() is called
	    verify(guestProfileRepository, times(2)).save(any(GuestProfile.class)); // Expect save to be called twice
	}


	@Test
	void testGetGuestById() {
		// Arrange
		String guestId = "guest1";
		GuestProfile expectedGuest = new GuestProfile();
		when(guestProfileRepository.findById(guestId)).thenReturn(Optional.of(expectedGuest));

		// Act
		Optional<GuestProfile> result = service.getGuestById(guestId);

		// Assert
		assertTrue(result.isPresent());
		assertSame(expectedGuest, result.get());
		verify(guestProfileRepository).findById(guestId);
	}

	@Test
	void testCreateGuest() {
		// Arrange
		GuestProfile guest = new GuestProfile();
		when(guestProfileRepository.save(any(GuestProfile.class))).thenReturn(guest);

		// Act
		GuestProfile result = service.createGuest(guest);

		// Assert
		assertNotNull(result);
		verify(guestProfileRepository).save(guest);
	}

	@Test
	void testUpdateGuest_NotFound() {
		// Arrange
		String guestId = "nonExistingId";
		GuestApplicationDTO dto = new GuestApplicationDTO();
		when(guestProfileRepository.findById(guestId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> service.updateGuest(guestId, dto));
	}

	@Test
	void testTrackApplication_NotFound() {
		// Arrange
		String guestId = "guest1";
		when(guestProfileRepository.findById(guestId)).thenReturn(Optional.empty());

		// Act
		GuestProfileTrackApplicationDTO result = service.trackApplication(guestId);

		// Assert
		assertNull(result);
	}

	@Test
	void testSaveGuestProfile() {
		GuestProfile guest = new GuestProfile();
		guest.setGuestEmail("test@example.com");
		when(guestProfileRepository.save(any(GuestProfile.class))).thenReturn(guest);

		GuestProfile savedGuest = service.save(guest);

		verify(guestProfileRepository).save(guest);
		assertEquals("test@example.com", savedGuest.getGuestEmail());
	}

	@Test
	void testGetGuestByPanNumber() {
		GuestProfile guest = new GuestProfile();
		guest.setGuestEmail("test@example.com");
		Optional<GuestProfile> optionalGuest = Optional.of(guest);
		when(guestProfileRepository.findById("test@example.com")).thenReturn(optionalGuest);

		Optional<GuestProfile> retrievedGuest = service.getGuestByPanNumber("test@example.com");

		verify(guestProfileRepository).findById("test@example.com");
		assertEquals("test@example.com", retrievedGuest.orElse(new GuestProfile()).getGuestEmail());
	}



}
