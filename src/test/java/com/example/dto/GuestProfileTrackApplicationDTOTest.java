package com.example.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.example.model.ApplicationStatus;

public class GuestProfileTrackApplicationDTOTest {

    @Test
    public void testNoArgsConstructor() {
        GuestProfileTrackApplicationDTO dto = new GuestProfileTrackApplicationDTO();
        
        assertNull(dto.getName());
        assertNull(dto.getCardType());
        assertNull(dto.getApplicationId());
        assertNull(dto.getApplicationStatus());
    }

    @Test
    public void testAllArgsConstructor() {
        String name = "John Doe";
        String cardType = "Visa";
        Long applicationId = 12345L;
        ApplicationStatus applicationStatus = ApplicationStatus.PENDING;

        GuestProfileTrackApplicationDTO dto = new GuestProfileTrackApplicationDTO(name, cardType, applicationId, applicationStatus);

        assertEquals(name, dto.getName());
        assertEquals(cardType, dto.getCardType());
        assertEquals(applicationId, dto.getApplicationId());
        assertEquals(applicationStatus, dto.getApplicationStatus());
    }

    @Test
    public void testSettersAndGetters() {
        GuestProfileTrackApplicationDTO dto = new GuestProfileTrackApplicationDTO();

        String name = "John Doe";
        String cardType = "Visa";
        Long applicationId = 12345L;
        ApplicationStatus applicationStatus = ApplicationStatus.PENDING;

        dto.setName(name);
        dto.setCardType(cardType);
        dto.setApplicationId(applicationId);
        dto.setApplicationStatus(applicationStatus);

        assertEquals(name, dto.getName());
        assertEquals(cardType, dto.getCardType());
        assertEquals(applicationId, dto.getApplicationId());
        assertEquals(applicationStatus, dto.getApplicationStatus());
    }
}
