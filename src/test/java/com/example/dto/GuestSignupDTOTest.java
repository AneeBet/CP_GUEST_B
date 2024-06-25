package com.example.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuestSignupDTOTest {

    private GuestSignupDTO guestSignupDTO;

    @BeforeEach
    public void setUp() {
        guestSignupDTO = new GuestSignupDTO();
    }

    @Test
    public void testDefaultConstructor() {
        assertNull(guestSignupDTO.getName());
        assertNull(guestSignupDTO.getEmail());
        assertNull(guestSignupDTO.getPassword());
    }

    @Test
    public void testParameterizedConstructor() {
        GuestSignupDTO dto = new GuestSignupDTO("John Doe", "john.doe@example.com", "password123");

        assertEquals("John Doe", dto.getName());
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
    }

    @Test
    public void testSetAndGetName() {
        guestSignupDTO.setName("Jane Doe");
        assertEquals("Jane Doe", guestSignupDTO.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        guestSignupDTO.setGuestEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", guestSignupDTO.getEmail());
    }

    @Test
    public void testSetAndGetPassword() {
        guestSignupDTO.setPassword("password456");
        assertEquals("password456", guestSignupDTO.getPassword());
    }
}
