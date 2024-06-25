package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.GuestGrievance;

@Repository
public interface GrievanceRepository extends JpaRepository<GuestGrievance, Long> {
	@Query(value = "SELECT * FROM guest_grievance WHERE guest_email = ?1", nativeQuery = true)
	List<GuestGrievance> findAllByGuestId(String guestId);
}
