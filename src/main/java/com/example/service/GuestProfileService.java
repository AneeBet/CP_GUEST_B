package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.dto.GuestApplicationDTO;
import com.example.dto.GuestProfileTrackApplicationDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.CreditCard;
import com.example.model.GuestProfile;
import com.example.repository.CreditCardRepository;
import com.example.repository.GuestProfileRepository;
import com.example.util.Hasher;
import com.example.util.KeyGenerator;


@Service
public class GuestProfileService {

	private GuestProfileRepository guestProfileRepository;

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private KeyGenerator generator;
	

	public GuestProfileService(GuestProfileRepository guestProfileRepository) {
		this.guestProfileRepository = guestProfileRepository;
	}

	public Optional<GuestProfile> getGuestById(String guestId) {
		return guestProfileRepository.findById(guestId);
	}

	public GuestProfile createGuest(GuestProfile guestProfile) {
		return guestProfileRepository.save(guestProfile);
	}

	public GuestProfile save(GuestProfile application) {
		return guestProfileRepository.save(application);
	}

	public Optional<GuestProfile> getGuestByPanNumber(String email) {
		return (guestProfileRepository.findById(email));
	}

	public GuestProfile updateGuest(String guestId, GuestApplicationDTO guestDetails)
			throws ResourceNotFoundException {
		Optional<GuestProfile> guestProfileOptional = guestProfileRepository.findById(guestId);
		if (guestProfileOptional.isPresent()) {
			GuestProfile guestProfile = guestProfileOptional.get();
 
			guestProfile.setAadhaarNumber(guestDetails.getAadhaarNumber());
			guestProfile.setAadhaarFilePath(guestDetails.getAadhaarFilePath());
			guestProfile.setApplicationId(generator.generateUniqueApplicationId());
			guestProfile.setApplicationStatus(guestDetails.getApplicationStatus());
			guestProfile.setName(guestDetails.getName());
			guestProfile.setAddress(guestDetails.getAddress());
			guestProfile.setMobileNumber(guestDetails.getMobileNumber());
			guestProfile.setDob(guestDetails.getDob());
			guestProfile.setEmploymentYears(guestDetails.getEmploymentYears());
			guestProfile.setCompanyName(guestDetails.getCompanyName());
			guestProfile.setAnnualIncome(guestDetails.getAnnualIncome());
			guestProfile.setIncomeProofFilePath(guestDetails.getIncomeProofFilePath());
			guestProfile.setPanId(guestDetails.getPanId());
			guestProfile.setPanFilePath(guestDetails.getPanFilePath());
			guestProfile.setSignatureFilePath(guestDetails.getSignatureFilePath());
			guestProfile.setPhotoFilePath(guestDetails.getPhotoFilePath());
			guestProfile.setAdmin(guestDetails.getUsername());
 
			// Handle CreditCard entity
			CreditCard creditCard = new CreditCard();
			creditCard.setCardType(guestDetails.getCardType());
			if (creditCard != null) {
				// Check if the creditCard already exists
				Optional<CreditCard> existingCreditCard = creditCardRepository.findById(creditCard.getCardType());
				if (existingCreditCard.isPresent()) {
					guestProfile.setCreditCard(existingCreditCard.get());
					
				}
			}
 
			GuestProfile guest = guestProfileRepository.save(guestProfile);
 
			sendEmail(guest.getGuestEmail(), "Application Submitted Successfully",
					"Hi, your application is Submitted with Id: " + guest.getApplicationId());
 
			return guestProfileRepository.save(guestProfile);
		} else {
			throw new ResourceNotFoundException("GuestProfile not found for this id :: " + guestId);
		}
	}

	public ResponseEntity<String> loginGuest(String email, String password) {
		Optional<GuestProfile> guestProfile = guestProfileRepository.findByGuestEmailAndPassword(email, Hasher.hashPassword(password));
		if (guestProfile.isPresent()) {
			return ResponseEntity.ok("Guest logged in successfully!");
		} else {
			return ResponseEntity.badRequest().body("Invalid email or password");
		}
	}

	public GuestProfileTrackApplicationDTO trackApplication(String guestId) {
		return guestProfileRepository
				.findById(guestId).map(guest -> new GuestProfileTrackApplicationDTO(guest.getName(),
						guest.getCreditCard().getCardType(), guest.getApplicationId(), guest.getApplicationStatus()))
				.orElse(null);
	}

	@Autowired
	private JavaMailSender mailSender;

	void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}
}
