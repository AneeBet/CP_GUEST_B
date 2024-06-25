package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.model.GuestGrievance;
import com.example.model.GuestProfile;
import com.example.model.ResolutionStatus;

public class GuestGrievanceTest {

    private GuestGrievance grievance;
    private GuestProfile guestProfile;

    @BeforeEach
    public void setUp() {
        guestProfile = Mockito.mock(GuestProfile.class); // Mocking the GuestProfile for isolation
        grievance = new GuestGrievance(1L, guestProfile, "Complaint about service", "Detailed description of the issue", new Date(), ResolutionStatus.PENDING);
    }

    @Test
    public void testConstructorAndGetter() {
        assertEquals(1L, grievance.getGrievanceId());
        assertEquals(guestProfile, grievance.getGuestProfile());
        assertEquals("Complaint about service", grievance.getSubject());
        assertEquals("Detailed description of the issue", grievance.getDescription());
        assertNotNull(grievance.getTimestamp());
        assertEquals(ResolutionStatus.PENDING, grievance.getStatus());
    }

    @Test
    public void testSetter() {
        grievance.setGrievanceId(2L);
        grievance.setSubject("New Complaint");
        grievance.setDescription("New detailed description");
        grievance.setTimestamp(new Date());
        grievance.setStatus(ResolutionStatus.RESOLVED);

        assertEquals(2L, grievance.getGrievanceId());
        assertEquals("New Complaint", grievance.getSubject());
        assertEquals("New detailed description", grievance.getDescription());
        assertNotNull(grievance.getTimestamp());
        assertEquals(ResolutionStatus.RESOLVED, grievance.getStatus());
    }
}
