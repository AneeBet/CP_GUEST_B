package com.example.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

	private final GuestProfileRepository guestProfileRepository;

	private final PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private JavaMailSender mailSender;

	public AuthService(GuestProfileRepository guestProfileRepository,
			PasswordResetTokenRepository passwordResetTokenRepository) {
		this.guestProfileRepository = guestProfileRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}

	private Map<String, GuestProfile> signupPendingGuests = new HashMap<>();

	public ResponseEntity<String> sendSignupOtp( GuestSignupDTO guestSignupDTO) {
		if (guestProfileRepository.findById(guestSignupDTO.getEmail()).isPresent()) {
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
		return ResponseEntity.ok("OTP sent to " + guestSignupDTO.getEmail());
	}

	public boolean verifySignupOtp(String email, String otp) {
		if (signupPendingGuests.containsKey(otp) && signupPendingGuests.get(otp).getGuestEmail().equals(email)) {
			
			GuestProfile guestProfile = signupPendingGuests.remove(otp);
			
			guestProfileRepository.save(guestProfile);
			return true;
		}
		return false;
	}

	public boolean sendPasswordResetOtp(String email) {
		System.out.println(email);
		Optional<GuestProfile> user = guestProfileRepository.findById(email);
		if (user.isPresent()) {
			String otp = generateOtp();
			PasswordResetToken token = new PasswordResetToken();
			token.setEmail(email);
			token.setOtp(otp);
			token.setExpiryTime(LocalDateTime.now().plusMinutes(3));
			passwordResetTokenRepository.save(token);
			sendEmail(email, otp);
			return true;
		}else{
			return false;
		}
	}

	public boolean verifyPasswordResetOtp(String email, String otp) {
		Optional<PasswordResetToken> token = passwordResetTokenRepository.findByEmailAndOtp(email, otp);
		return (token.isPresent() && token.get().getExpiryTime().isAfter(LocalDateTime.now()));

	}

	@Transactional
	public void resetPassword(String email, String otp, String newPassword) {
		if (verifyPasswordResetOtp(email, otp)) {
			Optional<GuestProfile> userOptional = guestProfileRepository.findById(email);
			if (userOptional.isPresent()) {
				GuestProfile user = userOptional.get();
				user.setPassword(Hasher.hashPassword(newPassword));
				guestProfileRepository.save(user);
				passwordResetTokenRepository.deleteByEmail(email);
			}
		}
	}

	private void sendEmail(String to, String otp) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Your OTP Code");
		message.setText("Your OTP code is: " + otp);
		mailSender.send(message);

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
