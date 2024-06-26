package com.example.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(GuestProfileService.class);

    private final GuestProfileRepository guestProfileRepository;
    private final CreditCardRepository creditCardRepository;
    private final KeyGenerator generator;
    
    @Autowired
    private JavaMailSender mailSender;

    public GuestProfileService(GuestProfileRepository guestProfileRepository,
                               CreditCardRepository creditCardRepository,
                               KeyGenerator generator) {
        this.guestProfileRepository = guestProfileRepository;
        this.creditCardRepository = creditCardRepository;
        this.generator = generator;
    }

    public Optional<GuestProfile> getGuestById(String guestId) {
        logger.info("Fetching guest profile by ID: {}", guestId);
        return guestProfileRepository.findById(guestId);
    }

    public GuestProfile createGuest(GuestProfile guestProfile) {
        logger.info("Creating guest profile for guest ID: {}", guestProfile.getGuestEmail());
        return guestProfileRepository.save(guestProfile);
    }

    public GuestProfile save(GuestProfile application) {
        logger.info("Saving guest application for guest ID: {}", application.getGuestEmail());
        return guestProfileRepository.save(application);
    }

    public Optional<GuestProfile> getGuestByPanNumber(String email) {
        logger.info("Fetching guest profile by PAN number: {}", email);
        return guestProfileRepository.findById(email);
    }

    public GuestProfile updateGuest(String guestId, GuestApplicationDTO guestDetails) throws ResourceNotFoundException {
        logger.info("Updating guest profile for guest ID: {}", guestId);
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

            CreditCard creditCard = new CreditCard();
            creditCard.setCardType(guestDetails.getCardType());
            if (creditCard != null) {
                Optional<CreditCard> existingCreditCard = creditCardRepository.findById(creditCard.getCardType());
                if (existingCreditCard.isPresent()) {
                    guestProfile.setCreditCard(existingCreditCard.get());
                }
            }

            GuestProfile guest = guestProfileRepository.save(guestProfile);

            sendEmail(guest.getGuestEmail(), "Application Submitted Successfully",
                    "Hi, your application is Submitted with Id: " + guest.getApplicationId());

            logger.info("Guest profile updated successfully for guest ID: {}", guestId);
            return guestProfileRepository.save(guestProfile);
        } else {
            logger.error("GuestProfile not found for ID: {}", guestId);
            throw new ResourceNotFoundException("GuestProfile not found for this id :: " + guestId);
        }
    }

    public ResponseEntity<String> loginGuest(String email, String password) {
        logger.info("Guest login attempt for email: {}", email);
        Optional<GuestProfile> guestProfile = guestProfileRepository.findByGuestEmailAndPassword(email, Hasher.hashPassword(password));
        if (guestProfile.isPresent()) {
            logger.info("Guest logged in successfully for email: {}", email);
            return ResponseEntity.ok("Guest logged in successfully!");
        } else {
            logger.warn("Invalid email or password for email: {}", email);
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    public GuestProfileTrackApplicationDTO trackApplication(String guestId) {
        logger.info("Tracking application for guest ID: {}", guestId);
        return guestProfileRepository.findById(guestId)
                .map(guest -> new GuestProfileTrackApplicationDTO(guest.getName(),
                        guest.getCreditCard().getCardType(), guest.getApplicationId(), guest.getApplicationStatus()))
                .orElse(null);
    }

    private void sendEmail(String to, String subject, String text) {
        logger.info("Sending email to: {}, subject: {}", to, subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        logger.info("Email sent to: {}", to);
    }
}
