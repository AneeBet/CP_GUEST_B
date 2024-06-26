package com.example.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuestProfileTest {

    private GuestProfile guestProfile;
    private Admin admin;
    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        admin = new Admin();
        admin.setUsername("adminUser");

        creditCard = new CreditCard();
        creditCard.setCardType("Visa");

        guestProfile = new GuestProfile(
                "test@example.com",
                "password123",
                123L,
                "1234567890",
                "John Doe",
                "ABCDE1234F",
                123456789012L,
                "123 Test St",
                new Date(1000000000L),
                5,
                "Test Company",
                new BigDecimal("50000"),
                "incomeProofPath",
                creditCard,
                ApplicationStatus.PENDING,
                "aadhaarFilePath",
                "panFilePath",
                "signatureFilePath",
                "photoFilePath",
                admin
        );
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("test@example.com", guestProfile.getGuestEmail());
        assertEquals("password123", guestProfile.getPassword());
        assertEquals(123L, guestProfile.getApplicationId());
        assertEquals("1234567890", guestProfile.getMobileNumber());
        assertEquals("John Doe", guestProfile.getName());
        assertEquals("ABCDE1234F", guestProfile.getPanId());
        assertEquals(123456789012L, guestProfile.getAadhaarNumber());
        assertEquals("123 Test St", guestProfile.getAddress());
        assertEquals(new Date(1000000000L), guestProfile.getDob());
        assertEquals(5, guestProfile.getEmploymentYears());
        assertEquals("Test Company", guestProfile.getCompanyName());
        assertEquals(new BigDecimal("50000"), guestProfile.getAnnualIncome());
        assertEquals("incomeProofPath", guestProfile.getIncomeProofFilePath());
        assertEquals(creditCard, guestProfile.getCreditCard());
        assertEquals(ApplicationStatus.PENDING, guestProfile.getApplicationStatus());
        assertEquals("aadhaarFilePath", guestProfile.getAadhaarFilePath());
        assertEquals("panFilePath", guestProfile.getPanFilePath());
        assertEquals("signatureFilePath", guestProfile.getSignatureFilePath());
        assertEquals("photoFilePath", guestProfile.getPhotoFilePath());
        assertEquals(admin, guestProfile.getAdmin());

        guestProfile.setGuestEmail("newemail@example.com");
        guestProfile.setPassword("newpassword");
        guestProfile.setApplicationId(456L);
        guestProfile.setMobileNumber("0987654321");
        guestProfile.setName("Jane Doe");
        guestProfile.setPanId("XYZAB1234C");
        guestProfile.setAadhaarNumber(987654321012L);
        guestProfile.setAddress("456 New St");
        guestProfile.setDob(new Date(2000000000L));
        guestProfile.setEmploymentYears(10);
        guestProfile.setCompanyName("New Company");
        guestProfile.setAnnualIncome(new BigDecimal("100000"));
        guestProfile.setIncomeProofFilePath("newIncomeProofPath");
        guestProfile.setCreditCard(null);
        guestProfile.setApplicationStatus(ApplicationStatus.APPROVED);
        guestProfile.setAadhaarFilePath("newAadhaarFilePath");
        guestProfile.setPanFilePath("newPanFilePath");
        guestProfile.setSignatureFilePath("newSignatureFilePath");
        guestProfile.setPhotoFilePath("newPhotoFilePath");
        guestProfile.setAdmin(null);

        assertEquals("newemail@example.com", guestProfile.getGuestEmail());
        assertEquals("newpassword", guestProfile.getPassword());
        assertEquals(456L, guestProfile.getApplicationId());
        assertEquals("0987654321", guestProfile.getMobileNumber());
        assertEquals("Jane Doe", guestProfile.getName());
        assertEquals("XYZAB1234C", guestProfile.getPanId());
        assertEquals(987654321012L, guestProfile.getAadhaarNumber());
        assertEquals("456 New St", guestProfile.getAddress());
        assertEquals(new Date(2000000000L), guestProfile.getDob());
        assertEquals(10, guestProfile.getEmploymentYears());
        assertEquals("New Company", guestProfile.getCompanyName());
        assertEquals(new BigDecimal("100000"), guestProfile.getAnnualIncome());
        assertEquals("newIncomeProofPath", guestProfile.getIncomeProofFilePath());
        assertNull(guestProfile.getCreditCard());
        assertEquals(ApplicationStatus.APPROVED, guestProfile.getApplicationStatus());
        assertEquals("newAadhaarFilePath", guestProfile.getAadhaarFilePath());
        assertEquals("newPanFilePath", guestProfile.getPanFilePath());
        assertEquals("newSignatureFilePath", guestProfile.getSignatureFilePath());
        assertEquals("newPhotoFilePath", guestProfile.getPhotoFilePath());
        assertNull(guestProfile.getAdmin());
    }

    @Test
    public void testNoArgsConstructor() {
        GuestProfile emptyGuestProfile = new GuestProfile();
        assertNotNull(emptyGuestProfile);
    }
}
