package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CreditCardTest {

    @Test
    void testCreditCardDefaultConstructor() {
        CreditCard creditCard = new CreditCard();
        assertEquals(null, creditCard.getCardType());
        assertEquals(null, creditCard.getMaxLimit());
        assertEquals(null, creditCard.getInterest());
        assertEquals(null, creditCard.getAnnualFee());
    }

    @Test
    void testCreditCardParameterizedConstructor() {
        BigDecimal maxLimit = new BigDecimal("5000.00");
        BigDecimal interest = new BigDecimal("5.0");
        BigDecimal annualFee = new BigDecimal("100.00");

        CreditCard creditCard = new CreditCard("Visa", maxLimit, interest, annualFee);
        assertEquals("Visa", creditCard.getCardType());
        assertEquals(maxLimit, creditCard.getMaxLimit());
        assertEquals(interest, creditCard.getInterest());
        assertEquals(annualFee, creditCard.getAnnualFee());
    }

    @Test
    void testGetAndSetCardType() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardType("MasterCard");
        assertEquals("MasterCard", creditCard.getCardType());
    }

    @Test
    void testGetAndSetMaxLimit() {
        CreditCard creditCard = new CreditCard();
        BigDecimal maxLimit = new BigDecimal("10000.00");
        creditCard.setMaxLimit(maxLimit);
        assertEquals(maxLimit, creditCard.getMaxLimit());
    }

    @Test
    void testGetAndSetInterest() {
        CreditCard creditCard = new CreditCard();
        BigDecimal interest = new BigDecimal("10.0");
        creditCard.setInterest(interest);
        assertEquals(interest, creditCard.getInterest());
    }

    @Test
    void testGetAndSetAnnualFee() {
        CreditCard creditCard = new CreditCard();
        BigDecimal annualFee = new BigDecimal("200.00");
        creditCard.setAnnualFee(annualFee);
        assertEquals(annualFee, creditCard.getAnnualFee());
    }
}
