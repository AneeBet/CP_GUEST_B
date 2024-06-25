package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.exception.BadRequestException;
import com.example.model.GuestFeedback;
import com.example.model.GuestGrievance;
import com.example.model.GuestScheduleCall;
import com.example.repository.FeedbackRepository;
import com.example.repository.GrievanceRepository;
import com.example.repository.ScheduleCallRepository;

public class GuestAssistanceServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private ScheduleCallRepository scheduleCallRepository;

    @Mock
    private GrievanceRepository grievanceRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private GuestAssistanceService guestAssistanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFeedback_Success() {
        GuestFeedback feedback = new GuestFeedback();
        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        GuestFeedback result = guestAssistanceService.createFeedback(feedback);

        assertEquals(feedback, result);
    }

    @Test
    public void testCreateGrievance_Success() throws BadRequestException {
        GuestGrievance grievance = new GuestGrievance();
        GuestGrievance savedGrievance = new GuestGrievance();
        savedGrievance.setGrievanceId(1L);
        when(grievanceRepository.save(grievance)).thenReturn(savedGrievance);

    }

    @Test
    public void testCreateGrievance_NullGrievance() {
        assertThrows(BadRequestException.class, () -> {
            guestAssistanceService.createGrievance(null);
        });
    }

    @Test
    public void testAddScheduleCall_Success() throws BadRequestException {
        GuestScheduleCall scheduleCall = new GuestScheduleCall();
        GuestScheduleCall savedScheduleCall = new GuestScheduleCall();
        savedScheduleCall.setScheduleCallId(1L);
        when(scheduleCallRepository.save(scheduleCall)).thenReturn(savedScheduleCall);

  
    }

    @Test
    public void testAddScheduleCall_NullScheduleCall() {
        assertThrows(BadRequestException.class, () -> {
            guestAssistanceService.addScheduleCall(null);
        });
    }

    @Test
    public void testGetAllGrievances() {
        String guestId = "guest123";
        List<GuestGrievance> grievances = new ArrayList<>();
        when(grievanceRepository.findAllByGuestId(guestId)).thenReturn(grievances);

        List<GuestGrievance> result = guestAssistanceService.getAllGrievances(guestId);

        assertEquals(grievances, result);
    }
}
