package com.example.service;

import com.example.exception.BadRequestException;
import com.example.model.GuestFeedback;
import com.example.model.GuestGrievance;
import com.example.model.GuestProfile;
import com.example.model.GuestScheduleCall;
import com.example.repository.FeedbackRepository;
import com.example.repository.GrievanceRepository;
import com.example.repository.ScheduleCallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    private GuestProfile guestProfile;

    @BeforeEach
    void setUp() {
        guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("test@example.com");
    }

    @Test
    void testCreateFeedback() {
        GuestFeedback feedback = new GuestFeedback();
        feedback.setGuestProfile(guestProfile);

        when(feedbackRepository.save(any(GuestFeedback.class))).thenReturn(feedback);

        GuestFeedback result = guestAssistanceService.createFeedback(feedback);

        assertNotNull(result);
        assertEquals("test@example.com", result.getGuestProfile().getGuestEmail());
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void testCreateGrievance() throws BadRequestException {
        GuestGrievance grievance = new GuestGrievance();
        grievance.setGuestProfile(guestProfile);




    }

    @Test
    void testCreateGrievanceWithNull() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> guestAssistanceService.createGrievance(null));
        assertEquals("Grievance data cannot be null", exception.getMessage());
    }

    @Test
    void testAddScheduleCall() throws BadRequestException {
        GuestScheduleCall scheduleCall = new GuestScheduleCall();
        scheduleCall.setGuestProfile(guestProfile);



       
    }

    @Test
    void testAddScheduleCallWithNull() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> guestAssistanceService.addScheduleCall(null));
        assertEquals("Schedule call data cannot be null", exception.getMessage());
    }

    @Test
    void testGetAllGrievances() {
        List<GuestGrievance> grievances = Collections.singletonList(new GuestGrievance());

        when(grievanceRepository.findAllByGuestId(anyString())).thenReturn(grievances);

        List<GuestGrievance> result = guestAssistanceService.getAllGrievances("guestId");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(grievanceRepository, times(1)).findAllByGuestId("guestId");
    }

   
}
