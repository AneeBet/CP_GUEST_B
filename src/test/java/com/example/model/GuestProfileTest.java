package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

class GuestProfileTest {
//    @Test
//    void testGuestProfileSettersAndGetters() {
//        // Create an instance of the model
//    	Admin admin = new Admin("Admin","1234");
//        GuestProfile guest = new GuestProfile();
//        guest.setGuestEmail("test@example.com");
//        guest.setPassword("password123");
//        guest.setApplicationId(12345L);
//        guest.setMobileNumber("1234567890");
//        guest.setName("John Doe");
//        guest.setPanId("ABCDE1234F");
//        guest.setAadhaarNumber(123456789012L);
//        guest.setAddress("1234 Main St");
//        guest.setDob(new Date());
//        guest.setEmploymentYears(5);
//        guest.setCompanyName("Example Corp");
//        guest.setAnnualIncome(new BigDecimal("50000"));
//        guest.setIncomeProofFilePath("/path/to/doc.pdf");
//        guest.setAadhaarFilePath("/path/to/aadhaar.pdf");
//        guest.setPanFilePath("/path/to/pan.pdf");
//        guest.setSignatureFilePath("/path/to/signature.pdf");
//        guest.setPhotoFilePath("/path/to/photo.jpg");
//        guest.setAdmin(admin);
//        guest.setApplicationId(1L);
//
//        // Assertions to check if the data is set correctly
//        assertEquals("test@example.com", guest.getGuestEmail());
//        assertEquals("password123", guest.getPassword());
//        assertEquals(12345L, guest.getApplicationId());
//        assertEquals("1234567890", guest.getMobileNumber());
//        assertEquals("John Doe", guest.getName());
//        assertEquals("ABCDE1234F", guest.getPanId());
//        assertEquals(Long.valueOf(123456789012L), guest.getAadhaarNumber());
//        assertEquals("1234 Main St", guest.getAddress());
//        assertNotNull(guest.getDob());
//        assertEquals(5, guest.getEmploymentYears());
//        assertEquals("Example Corp", guest.getCompanyName());
//        assertEquals(0, new BigDecimal("50000").compareTo(guest.getAnnualIncome()));
//        assertEquals("/path/to/doc.pdf", guest.getIncomeProofFilePath());
//        assertEquals("/path/to/aadhaar.pdf", guest.getAadhaarFilePath());
//        assertEquals("/path/to/pan.pdf", guest.getPanFilePath());
//        assertEquals("/path/to/signature.pdf", guest.getSignatureFilePath());
//        assertEquals("/path/to/photo.jpg", guest.getPhotoFilePath());
//        assertEquals("Admin",admin.getUsername());
//        assertEquals("1234",admin.getPassword());
//        assertEquals(1L,guest.getApplicationId());
//        
//    }
    
    @Test
    public void testGuestProfileConstructor() {
        // Setup
        Admin admin = new Admin("Admin", "1234");
        Date now = new Date();
        GuestProfile guest = new GuestProfile(
            "test@example.com", "password123", 12345L, "1234567890", "John Doe",
            "ABCDE1234F", 123456789012L, "1234 Main St", now, 5, "Example Corp",
            new BigDecimal("50000"), "/path/to/doc.pdf", new CreditCard(), 
            ApplicationStatus.APPROVED, "/path/to/aadhaar.pdf", "/path/to/pan.pdf",
            "/path/to/signature.pdf", "/path/to/photo.jpg", admin
        );

        // Assertions
        assertEquals("test@example.com", guest.getGuestEmail());
        assertEquals("password123", guest.getPassword());
        assertEquals(12345L, guest.getApplicationId());
        assertEquals("1234567890", guest.getMobileNumber());
        assertEquals("John Doe", guest.getName());
        assertEquals("ABCDE1234F", guest.getPanId());
        assertEquals(Long.valueOf(123456789012L), guest.getAadhaarNumber());
        assertEquals("1234 Main St", guest.getAddress());
        assertEquals(now, guest.getDob());
        assertEquals(5, guest.getEmploymentYears());
        assertEquals("Example Corp", guest.getCompanyName());
        assertEquals(0, new BigDecimal("50000").compareTo(guest.getAnnualIncome()));
        assertEquals("/path/to/doc.pdf", guest.getIncomeProofFilePath());
        assertEquals("/path/to/aadhaar.pdf", guest.getAadhaarFilePath());
        assertEquals("/path/to/pan.pdf", guest.getPanFilePath());
        assertEquals("/path/to/signature.pdf", guest.getSignatureFilePath());
        assertEquals("/path/to/photo.jpg", guest.getPhotoFilePath());
        assertEquals(admin, guest.getAdmin());
        assertEquals("Admin", admin.getUsername());
        assertEquals("1234", admin.getPassword());
    }
}
