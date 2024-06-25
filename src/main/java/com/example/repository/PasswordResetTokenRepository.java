package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	@Query(value = "SELECT * FROM password_reset_token WHERE email = :email AND otp = :otp", nativeQuery = true)
    Optional<PasswordResetToken> findByEmailAndOtp(@Param("email") String email, @Param("otp") String otp);
    Optional<PasswordResetToken> deleteByEmail(String email);
}

