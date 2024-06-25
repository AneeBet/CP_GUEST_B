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
        guestGrievance = new GuestGrievance();
        guestScheduleCall = new GuestScheduleCall();
    }

    @Test
    void testCreateFeedback() {
        when(guestAssistanceService.createFeedback(any(GuestFeedback.class))).thenReturn(guestFeedback);

        GuestFeedback result = guestAssistanceController.createFeedback(guestFeedback);

        assertEquals(guestFeedback, result);
        verify(guestAssistanceService, times(1)).createFeedback(guestFeedback);
    }

    @Test
    void testGetAllGrievances() {
        List<GuestGrievance> grievances = List.of(new GuestGrievance());
        when(guestAssistanceService.getAllGrievances("guest1")).thenReturn(grievances);

        List<GuestGrievance> result = guestAssistanceController.getAllGrievances("guest1");

        assertEquals(grievances, result);
        verify(guestAssistanceService, times(1)).getAllGrievances("guest1");
    }

    @Test
    void testCreateGrievance() throws BadRequestException {
        when(guestAssistanceService.createGrievance(any(GuestGrievance.class))).thenReturn(guestGrievance);

        GuestGrievance result = guestAssistanceController.createGrievance(guestGrievance);

        assertEquals(guestGrievance, result);
        verify(guestAssistanceService, times(1)).createGrievance(guestGrievance);
    }

    @Test
    void testAddScheduleCall() throws BadRequestException {
        when(guestAssistanceService.addScheduleCall(any(GuestScheduleCall.class))).thenReturn(guestScheduleCall);

        GuestScheduleCall result = guestAssistanceController.addScheduleCall(guestScheduleCall);

        assertEquals(guestScheduleCall, result);
        verify(guestAssistanceService, times(1)).addScheduleCall(guestScheduleCall);
    }

    @Test
    void testCreateGrievanceThrowsBadRequestException() throws BadRequestException {
        when(guestAssistanceService.createGrievance(any(GuestGrievance.class))).thenThrow(new BadRequestException("Grievance data cannot be null"));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            guestAssistanceController.createGrievance(guestGrievance);
        });

        assertEquals("Grievance data cannot be null", exception.getMessage());
        verify(guestAssistanceService, times(1)).createGrievance(guestGrievance);
    }

    @Test
    void testAddScheduleCallThrowsBadRequestException() throws BadRequestException {
        when(guestAssistanceService.addScheduleCall(any(GuestScheduleCall.class))).thenThrow(new BadRequestException("Schedule call data cannot be null"));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            guestAssistanceController.addScheduleCall(guestScheduleCall);
        });

        assertEquals("Schedule call data cannot be null", exception.getMessage());
        verify(guestAssistanceService, times(1)).addScheduleCall(guestScheduleCall);
    }
}
