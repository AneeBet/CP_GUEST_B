package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.GuestProfile;

@Repository
public interface GuestProfileRepository extends JpaRepository<GuestProfile, String> {
	@Query(value = "SELECT * FROM guest_profile WHERE guest_email = :guestEmail AND password = :password", nativeQuery = true)
    Optional<GuestProfile> findByGuestEmailAndPassword(@Param("guestEmail") String guestEmail, @Param("password") String password);

	boolean existsByApplicationId(Long applicationId);

}
