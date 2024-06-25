package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.model.GuestProfile;
import com.example.model.GuestScheduleCall;
import com.example.model.ResolutionStatus;

public class GuestScheduleCallTest {

    private GuestScheduleCall scheduleCall;
    private GuestProfile guestProfile;

    @BeforeEach
    public void setUp() {
        guestProfile = Mockito.mock(GuestProfile.class); // Mocking the GuestProfile as it's not the focus here
        scheduleCall = new GuestScheduleCall(1L, guestProfile, "Meeting", "Discuss updates", "2024-06-25T15:00:00", ResolutionStatus.PENDING);
    }

    @Test
    public void testConstructorAndGetter() {
        assertEquals(1L, scheduleCall.getScheduleCallId());
        assertEquals(guestProfile, scheduleCall.getGuestProfile());
        assertEquals("Meeting", scheduleCall.getSubject());
        assertEquals("Discuss updates", scheduleCall.getDescription());
        assertEquals("2024-06-25T15:00:00", scheduleCall.getTimeSlot());
        assertEquals(ResolutionStatus.PENDING, scheduleCall.getStatus());
    }

    @Test
    public void testSetter() {
        scheduleCall.setScheduleCallId(2L);
        scheduleCall.setSubject("New Meeting");
        scheduleCall.setDescription("Discuss further");
        scheduleCall.setTimeSlot("2024-06-26T15:00:00");
        scheduleCall.setStatus(ResolutionStatus.RESOLVED);

        assertEquals(2L, scheduleCall.getScheduleCallId());
        assertEquals("New Meeting", scheduleCall.getSubject());
        assertEquals("Discuss further", scheduleCall.getDescription());
        assertEquals("2024-06-26T15:00:00", scheduleCall.getTimeSlot());
        assertEquals(ResolutionStatus.RESOLVED, scheduleCall.getStatus());
    }
}
