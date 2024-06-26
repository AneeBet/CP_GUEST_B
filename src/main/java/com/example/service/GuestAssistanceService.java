package com.example.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.exception.BadRequestException;
import com.example.model.GuestFeedback;
import com.example.model.GuestGrievance;
import com.example.model.GuestScheduleCall;
import com.example.repository.FeedbackRepository;
import com.example.repository.GrievanceRepository;
import com.example.repository.ScheduleCallRepository;

@Service
public class GuestAssistanceService {

    private static final Logger logger = LoggerFactory.getLogger(GuestAssistanceService.class);

    private final FeedbackRepository feedbackRepository;
    private final ScheduleCallRepository scheduleCallRepository;
    private final GrievanceRepository grievanceRepository;

    @Autowired
    private JavaMailSender mailSender;

    public GuestAssistanceService(FeedbackRepository feedbackRepository, ScheduleCallRepository scheduleCallRepository,
                                  GrievanceRepository grievanceRepository) {
        this.feedbackRepository = feedbackRepository;
        this.scheduleCallRepository = scheduleCallRepository;
        this.grievanceRepository = grievanceRepository;
    }

    public GuestFeedback createFeedback(GuestFeedback feedback) {
        logger.info("Creating guest feedback for guest ID: {}", feedback.getGuestProfile().getGuestEmail());
        return feedbackRepository.save(feedback);
    }

    public GuestGrievance createGrievance(GuestGrievance grievance) throws BadRequestException {
        if (grievance == null) {
            logger.error("Grievance data cannot be null");
            throw new BadRequestException("Grievance data cannot be null");
        }

        sendEmail(grievance.getGuestProfile().getGuestEmail(), "Grievance Submitted Successfully",
                "Hi, your grievance is Submitted. Our Customer Support will try to resolve it as soon as possible");
        logger.info("Guest grievance created for guest ID: {}", grievance.getGuestProfile().getGuestEmail());
        return grievanceRepository.save(grievance);
    }

    public GuestScheduleCall addScheduleCall(GuestScheduleCall scheduleCall) throws BadRequestException {
        if (scheduleCall == null) {
            logger.error("Schedule call data cannot be null");
            throw new BadRequestException("Schedule call data cannot be null");
        }

        sendEmail(scheduleCall.getGuestProfile().getGuestEmail(), "ScheduleCall Submitted Successfully",
                "Hi, your ScheduleCall is Submitted. Our Customer Support will try to resolve it as soon as possible");
        logger.info("Guest schedule call added for guest ID: {}", scheduleCall.getGuestProfile().getGuestEmail());
        return scheduleCallRepository.save(scheduleCall);
    }

    private void sendEmail(String to, String subject, String text) {
        logger.info("Sending email to: {}, subject: {}", to, subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        logger.info("Email sent to: {}", to);
    }

    public List<GuestGrievance> getAllGrievances(String guestId) {
        logger.info("Fetching all grievances for guest ID: {}", guestId);
        return grievanceRepository.findAllByGuestId(guestId);
    }
}
