package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GuestFeedbackTest {

    @Test
    void testGuestFeedbackDefaultConstructor() {
        GuestFeedback guestFeedback = new GuestFeedback();
        assertEquals(null, guestFeedback.getFeedbackId());
        assertEquals(null, guestFeedback.getRating());
        assertEquals(null, guestFeedback.getGuestProfile());
        assertEquals(null, guestFeedback.getDescription());
    }

    @Test
    void testGuestFeedbackParameterizedConstructor() {
        Long feedbackId = 1L;
        Byte rating = 5;
        GuestProfile guestProfile = new GuestProfile();
        String description = "Great service!";

        GuestFeedback guestFeedback = new GuestFeedback(feedbackId, rating, guestProfile, description);
        assertEquals(feedbackId, guestFeedback.getFeedbackId());
        assertEquals(rating, guestFeedback.getRating());
        assertEquals(guestProfile, guestFeedback.getGuestProfile());
        assertEquals(description, guestFeedback.getDescription());
    }

    @Test
    void testGetAndSetFeedbackId() {
        GuestFeedback guestFeedback = new GuestFeedback();
        Long feedbackId = 1L;
        guestFeedback.setFeedbackId(feedbackId);
        assertEquals(feedbackId, guestFeedback.getFeedbackId());
    }

    @Test
    void testGetAndSetRating() {
        GuestFeedback guestFeedback = new GuestFeedback();
        Byte rating = 5;
        guestFeedback.setRating(rating);
        assertEquals(rating, guestFeedback.getRating());
    }

    @Test
    void testGetAndSetGuestProfile() {
        GuestFeedback guestFeedback = new GuestFeedback();
        GuestProfile guestProfile = new GuestProfile();
        guestFeedback.setGuestProfile(guestProfile);
        assertEquals(guestProfile, guestFeedback.getGuestProfile());
    }

    @Test
    void testGetAndSetDescription() {
        GuestFeedback guestFeedback = new GuestFeedback();
        String description = "Great service!";
        guestFeedback.setDescription(description);
        assertEquals(description, guestFeedback.getDescription());
    }
}
