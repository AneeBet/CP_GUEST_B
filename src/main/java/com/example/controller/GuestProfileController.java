package com.example.controller;
 
import java.util.Map;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.example.dto.GuestApplicationDTO;
import com.example.dto.GuestProfileTrackApplicationDTO;
import com.example.dto.GuestSignupDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.GuestProfile;
import com.example.service.AuthService;
import com.example.service.GuestProfileService;
 
@RestController
@RequestMapping("/guest")
@CrossOrigin(origins = "*")
public class GuestProfileController {
 
	private GuestProfileService guestProfileService;
 
	private AuthService authService;
	
	private static final String EMAIL = "email";
 
	public GuestProfileController(GuestProfileService guestProfileService, AuthService authService) {
		this.guestProfileService = guestProfileService;
		this.authService = authService;
	}
 
	
	//------------------Guest Profile Information----------------//
	@GetMapping("/readone/{guestId}")
	public ResponseEntity<GuestProfile> getGuestById(@PathVariable("guestId") String guestId) {
		return guestProfileService.getGuestById(guestId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
 
	
	
	@PutMapping("/update/{email}")
	public ResponseEntity<GuestProfile> updateGuest(@PathVariable(EMAIL) String guestId,
			@RequestBody GuestApplicationDTO guestDetails) throws ResourceNotFoundException {
		return ResponseEntity.ok(guestProfileService.updateGuest(guestId, guestDetails));
	}
 
	@GetMapping("/track-application/{guestId}")
	public ResponseEntity<GuestProfileTrackApplicationDTO> trackApplication(@PathVariable("guestId") String guestId) {
		GuestProfileTrackApplicationDTO guestProfileTrackApplicationDTO = guestProfileService.trackApplication(guestId);
		if (guestProfileTrackApplicationDTO != null) {
			return ResponseEntity.ok(guestProfileTrackApplicationDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
 
	@PostMapping("/signup")
	public ResponseEntity<String> initiateSignup(@RequestBody GuestSignupDTO guestSignupDTO) {
		return authService.sendSignupOtp(guestSignupDTO);
	}
 
	@PostMapping("/verify-signup-otp")
	public ResponseEntity<String> verifySignupOtp(@RequestBody Map<String, String> payload) {
		String email = payload.get(EMAIL);
		String otp = payload.get("otp");
		boolean isVerified = authService.verifySignupOtp(email, otp);
		if (isVerified) {
			return ResponseEntity.ok("Guest registered successfully!");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
		}
	}
 
	@PostMapping("/login")
	public ResponseEntity<String> loginGuest(@RequestBody Map<String, String> loginRequest) {
		return guestProfileService.loginGuest(loginRequest.get(EMAIL), loginRequest.get("password"));
	}
 
	@PostMapping("/forgot-password")
	public boolean forgotPassword(@RequestBody Map<String, String> emailRequest) {
		if(	authService.sendPasswordResetOtp(emailRequest.get(EMAIL)))
			return true;
		else 
			return false;
		
	
	}
 
	@PostMapping("/verify-password-reset-otp")
	public ResponseEntity<Boolean> verifyPasswordResetOtp(@RequestBody Map<String, String> otpRequest) {
		boolean isVerified = authService.verifyPasswordResetOtp(otpRequest.get(EMAIL), otpRequest.get("otp"));
		return ResponseEntity.ok(isVerified);
	}
 
	@PostMapping("/reset-password")
	public void resetPassword(@RequestBody Map<String, String> resetPasswordRequest) {
		authService.resetPassword(resetPasswordRequest.get(EMAIL), resetPasswordRequest.get("otp"),
				resetPasswordRequest.get("password"));
	}
 
}