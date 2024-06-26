package com.example.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.dto.GuestSignupDTO;
import com.example.model.GuestProfile;
import com.example.model.PasswordResetToken;
import com.example.repository.GuestProfileRepository;
import com.example.repository.PasswordResetTokenRepository;
import com.example.util.Hasher;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final GuestProfileRepository guestProfileRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, GuestProfile> signupPendingGuests = new HashMap<>();

    public AuthService(GuestProfileRepository guestProfileRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.guestProfileRepository = guestProfileRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public ResponseEntity<String> sendSignupOtp(GuestSignupDTO guestSignupDTO) {
        logger.info("Attempting to send signup OTP to email: {}", guestSignupDTO.getEmail());
        if (guestProfileRepository.findById(guestSignupDTO.getEmail()).isPresent()) {
            logger.warn("Email already exists: {}", guestSignupDTO.getEmail());
            return ResponseEntity.badRequest().body("Email already exists");
        }

        GuestProfile guestProfile = new GuestProfile();
        guestProfile.setName(guestSignupDTO.getName());
        guestProfile.setGuestEmail(guestSignupDTO.getEmail());
        String hashedPassword = Hasher.hashPassword(guestSignupDTO.getPassword());
        guestProfile.setPassword(hashedPassword);

        String otp = generateOtp();
        signupPendingGuests.put(otp, guestProfile);
        sendEmail(guestSignupDTO.getEmail(), otp);
        logger.info("OTP sent to email: {}", guestSignupDTO.getEmail());
        return ResponseEntity.ok("OTP sent to " + guestSignupDTO.getEmail());
    }

    public boolean verifySignupOtp(String email, String otp) {
        logger.info("Verifying signup OTP for email: {} with OTP: {}", email, otp);
        if (signupPendingGuests.containsKey(otp) && signupPendingGuests.get(otp).getGuestEmail().equals(email)) {
            GuestProfile guestProfile = signupPendingGuests.remove(otp);
            guestProfileRepository.save(guestProfile);
            logger.info("Signup OTP verified and guest profile created for email: {}", email);
            return true;
        }
        logger.warn("Invalid OTP for email: {}", email);
        return false;
    }

    public boolean sendPasswordResetOtp(String email) {
        logger.info("Sending password reset OTP to email: {}", email);
        Optional<GuestProfile> user = guestProfileRepository.findById(email);
        if (user.isPresent()) {
            String otp = generateOtp();
            PasswordResetToken token = new PasswordResetToken();
            token.setEmail(email);
            token.setOtp(otp);
            token.setExpiryTime(LocalDateTime.now().plusMinutes(3));
            passwordResetTokenRepository.save(token);
            sendEmail(email, otp);
            logger.info("Password reset OTP sent to email: {}", email);
            return true;
        } else {
            logger.warn("Email not found: {}", email);
            return false;
        }
    }

    public boolean verifyPasswordResetOtp(String email, String otp) {
        logger.info("Verifying password reset OTP for email: {} with OTP: {}", email, otp);
        try {
            Optional<PasswordResetToken> token = passwordResetTokenRepository.findByEmailAndOtp(email, otp);
            if (token.isPresent()) {
                PasswordResetToken retrievedToken = token.get();
                boolean isTokenValid = retrievedToken.getExpiryTime().isAfter(LocalDateTime.now());
                logger.info("Token valid: {}", isTokenValid);
                return isTokenValid;
            } else {
                logger.warn("No token found for email: {} with OTP: {}", email, otp);
                return false;
            }
        } catch (Exception e) {
            logger.error("An error occurred while verifying OTP for email: {} with OTP: {}", email, otp, e);
            return false;
        }
    }

    @Transactional
    public void resetPassword(String email, String otp, String newPassword) {
        logger.info("Resetting password for email: {} with OTP: {}", email, otp);
        if (verifyPasswordResetOtp(email, otp)) {
            Optional<GuestProfile> userOptional = guestProfileRepository.findById(email);
            if (userOptional.isPresent()) {
                GuestProfile user = userOptional.get();
                user.setPassword(Hasher.hashPassword(newPassword));
                guestProfileRepository.save(user);
                passwordResetTokenRepository.deleteByEmail(email);
                logger.info("Password reset successfully for email: {}", email);
            } else {
                logger.warn("User not found for email: {}", email);
            }
        } else {
            logger.warn("Invalid OTP for password reset for email: {}", email);
        }
    }

    private void sendEmail(String to, String otp) {
        logger.info("Sending email to: {}, with OTP: {}", to, otp);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
        logger.info("Email sent to: {}", to);
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public Map<String, GuestProfile> getSignupPendingGuests() {
        return signupPendingGuests;
    }

    public void setSignupPendingGuests(Map<String, GuestProfile> signupPendingGuests) {
        this.signupPendingGuests = signupPendingGuests;
    }
}
