package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.exception.BadRequestException;
import com.example.model.GuestFeedback;
import com.example.model.GuestGrievance;
import com.example.model.GuestScheduleCall;
import com.example.service.GuestAssistanceService;

public class GuestAssistanceControllerTest {

    @Mock
    private GuestAssistanceService guestAssistanceService;

    @InjectMocks
    private GuestAssistanceController guestAssistanceController;

    private GuestFeedback guestFeedback;
    private GuestGrievance guestGrievance;
    private GuestScheduleCall guestScheduleCall;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        guestFeedback = new GuestFeedback();
        guestFeedback.setDescription("Test feedback");
        guestGrievance = new GuestGrievance();
        guestGrievance.setDescription("Test grievance");
        guestScheduleCall = new GuestScheduleCall();
        guestScheduleCall.setDescription("Test schedule call");
    }

    @Test
    void testCreateFeedback() throws BadRequestException {
        when(guestAssistanceService.createFeedback(any(GuestFeedback.class))).thenReturn(guestFeedback);

        GuestFeedback result = guestAssistanceController.createFeedback(guestFeedback);

        assertEquals(guestFeedback, result);
        verify(guestAssistanceService, times(1)).createFeedback(guestFeedback);
    }

    @Test
    void testCreateFeedbackThrowsBadRequestException() {
        guestFeedback.setDescription(null); // Set invalid description to trigger exception

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            guestAssistanceController.createFeedback(guestFeedback);
        });

        assertEquals("Feedback description cannot be null or empty", exception.getMessage());
        verify(guestAssistanceService, times(0)).createFeedback(guestFeedback);
    }

    @Test
    void testGetAllGrievances() throws BadRequestException {
        List<GuestGrievance> grievances = List.of(new GuestGrievance());
        when(guestAssistanceService.getAllGrievances("guest1")).thenReturn(grievances);

        List<GuestGrievance> result = guestAssistanceController.getAllGrievances("guest1");

        assertEquals(grievances, result);
        verify(guestAssistanceService, times(1)).getAllGrievances("guest1");
    }

    @Test
    void testGetAllGrievancesThrowsBadRequestException() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            guestAssistanceController.getAllGrievances("");
        });

        assertEquals("Guest ID cannot be null or empty", exception.getMessage());
        verify(guestAssistanceService, times(0)).getAllGrievances(anyString());
    }

    @Test
    void testCreateGrievance() throws BadRequestException {
        when(guestAssistanceService.createGrievance(any(GuestGrievance.class))).thenReturn(guestGrievance);

        GuestGrievance result = guestAssistanceController.createGrievance(guestGrievance);

        assertEquals(guestGrievance, result);
        verify(guestAssistanceService, times(1)).createGrievance(guestGrievance);
    }

    @Test
    void testCreateGrievanceThrowsBadRequestException() throws BadRequestException {
        guestGrievance.setDescription(null); // Set invalid description to trigger exception

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            guestAssistanceController.createGrievance(guestGrievance);
        });

        assertEquals("Grievance description cannot be null or empty", exception.getMessage());
        verify(guestAssistanceService, times(0)).createGrievance(guestGrievance);
    }

    @Test
    void testAddScheduleCall() throws BadRequestException {
        when(guestAssistanceService.addScheduleCall(any(GuestScheduleCall.class))).thenReturn(guestScheduleCall);

        GuestScheduleCall result = guestAssistanceController.addScheduleCall(guestScheduleCall);

        assertEquals(guestScheduleCall, result);
        verify(guestAssistanceService, times(1)).addScheduleCall(guestScheduleCall);
    }

    @Test
    void testAddScheduleCallThrowsBadRequestException() throws BadRequestException {
        guestScheduleCall.setDescription(null); // Set invalid description to trigger exception

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            guestAssistanceController.addScheduleCall(guestScheduleCall);
        });

        assertEquals("Schedule call description cannot be null or empty", exception.getMessage());
        verify(guestAssistanceService, times(0)).addScheduleCall(guestScheduleCall);
    }
}
