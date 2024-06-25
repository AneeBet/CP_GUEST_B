package com.example.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Admin;
import com.example.model.ApplicationStatus;

public class GuestApplicationDTOTest {

    private GuestApplicationDTO guestApplicationDTO;

    @BeforeEach
    public void setUp() {
        guestApplicationDTO = new GuestApplicationDTO();
    }

    @Test
    public void testDefaultConstructor() {
        assertNull(guestApplicationDTO.getApplicationId());
        assertNull(guestApplicationDTO.getMobileNumber());
        assertNull(guestApplicationDTO.getName());
        assertNull(guestApplicationDTO.getPanId());
        assertNull(guestApplicationDTO.getAadhaarNumber());
        assertNull(guestApplicationDTO.getAddress());
        assertNull(guestApplicationDTO.getDob());
        assertNull(guestApplicationDTO.getEmploymentYears());
        assertNull(guestApplicationDTO.getCompanyName());
        assertNull(guestApplicationDTO.getAnnualIncome());
        assertNull(guestApplicationDTO.getIncomeProofFilePath());
        assertNull(guestApplicationDTO.getCardType());
        assertNull(guestApplicationDTO.getApplicationStatus());
        assertNull(guestApplicationDTO.getAadhaarFilePath());
        assertNull(guestApplicationDTO.getPanFilePath());
        assertNull(guestApplicationDTO.getSignatureFilePath());
        assertNull(guestApplicationDTO.getPhotoFilePath());
        assertNull(guestApplicationDTO.getUsername());
    }

    @Test
    public void testSetAndGetApplicationId() {
        guestApplicationDTO.setApplicationId(1L);
        assertEquals(1L, guestApplicationDTO.getApplicationId());
    }

    @Test
    public void testSetAndGetMobileNumber() {
        guestApplicationDTO.setMobileNumber("1234567890");
        assertEquals("1234567890", guestApplicationDTO.getMobileNumber());
    }

    @Test
    public void testSetAndGetName() {
        guestApplicationDTO.setName("John Doe");
        assertEquals("John Doe", guestApplicationDTO.getName());
    }

    @Test
    public void testSetAndGetPanId() {
        guestApplicationDTO.setPanId("ABCDE1234F");
        assertEquals("ABCDE1234F", guestApplicationDTO.getPanId());
    }

    @Test
    public void testSetAndGetAadhaarNumber() {
        guestApplicationDTO.setAadhaarNumber(123456789012L);
        assertEquals(123456789012L, guestApplicationDTO.getAadhaarNumber());
    }

    @Test
    public void testSetAndGetAddress() {
        guestApplicationDTO.setAddress("123 Main St");
        assertEquals("123 Main St", guestApplicationDTO.getAddress());
    }

    @Test
    public void testSetAndGetDob() {
        Date dob = new Date();
        guestApplicationDTO.setDob(dob);
        assertEquals(dob, guestApplicationDTO.getDob());
    }

    @Test
    public void testSetAndGetEmploymentYears() {
        guestApplicationDTO.setEmploymentYears(5);
        assertEquals(5, guestApplicationDTO.getEmploymentYears());
    }

    @Test
    public void testSetAndGetCompanyName() {
        guestApplicationDTO.setCompanyName("Tech Corp");
        assertEquals("Tech Corp", guestApplicationDTO.getCompanyName());
    }

    @Test
    public void testSetAndGetAnnualIncome() {
        guestApplicationDTO.setAnnualIncome(new BigDecimal("50000"));
        assertEquals(new BigDecimal("50000"), guestApplicationDTO.getAnnualIncome());
    }

    @Test
    public void testSetAndGetIncomeProofFilePath() {
        guestApplicationDTO.setIncomeProofFilePath("path/to/incomeProof");
        assertEquals("path/to/incomeProof", guestApplicationDTO.getIncomeProofFilePath());
    }

    @Test
    public void testSetAndGetCardType() {
        guestApplicationDTO.setCardType("Visa");
        assertEquals("Visa", guestApplicationDTO.getCardType());
    }

    @Test
    public void testSetAndGetApplicationStatus() {
        guestApplicationDTO.setApplicationStatus(ApplicationStatus.PENDING);
        assertEquals(ApplicationStatus.PENDING, guestApplicationDTO.getApplicationStatus());
    }

    @Test
    public void testSetAndGetAadhaarFilePath() {
        guestApplicationDTO.setAadhaarFilePath("path/to/aadhaar");
        assertEquals("path/to/aadhaar", guestApplicationDTO.getAadhaarFilePath());
    }

    @Test
    public void testSetAndGetPanFilePath() {
        guestApplicationDTO.setPanFilePath("path/to/pan");
        assertEquals("path/to/pan", guestApplicationDTO.getPanFilePath());
    }

    @Test
    public void testSetAndGetSignatureFilePath() {
        guestApplicationDTO.setSignatureFilePath("path/to/signature");
        assertEquals("path/to/signature", guestApplicationDTO.getSignatureFilePath());
    }

    @Test
    public void testSetAndGetPhotoFilePath() {
        guestApplicationDTO.setPhotoFilePath("path/to/photo");
        assertEquals("path/to/photo", guestApplicationDTO.getPhotoFilePath());
    }

    @Test
    public void testSetAndGetUsername() {
        Admin admin = new Admin();
        guestApplicationDTO.setUsername(admin);
        assertEquals(admin, guestApplicationDTO.getUsername());
    }
}
