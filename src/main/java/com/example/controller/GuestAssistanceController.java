package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.BadRequestException;
import com.example.model.GuestFeedback;
import com.example.model.GuestGrievance;
import com.example.model.GuestScheduleCall;
import com.example.service.GuestAssistanceService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/guest")
public class GuestAssistanceController {

	private GuestAssistanceService guestAssistanceService;

	public GuestAssistanceController(GuestAssistanceService guestAssistanceService) {
		this.guestAssistanceService = guestAssistanceService;
	}


    @PostMapping("/feedback/add")
    public GuestFeedback createFeedback(@RequestBody GuestFeedback feedback) throws BadRequestException {
      
        if (feedback == null || feedback.getDescription() == null || feedback.getDescription().isEmpty()) {
            throw new BadRequestException("Feedback description cannot be null or empty");
        }
        return guestAssistanceService.createFeedback(feedback);
    }

    @GetMapping("/grievance/readall/{guestId}")
    public List<GuestGrievance> getAllGrievances(@PathVariable("guestId") String guestId) throws BadRequestException {
      
        if (guestId == null || guestId.isEmpty()) {
            throw new BadRequestException("Guest ID cannot be null or empty");
        }
        return guestAssistanceService.getAllGrievances(guestId);
    }

    @PostMapping("/grievance/add")
    public GuestGrievance createGrievance(@RequestBody GuestGrievance grievance) throws BadRequestException {
      
        if (grievance == null || grievance.getDescription() == null || grievance.getDescription().isEmpty()) {
            throw new BadRequestException("Grievance description cannot be null or empty");
        }
        return guestAssistanceService.createGrievance(grievance);
    }

    @PostMapping("/schedulecall/add")
    public GuestScheduleCall addScheduleCall(@RequestBody GuestScheduleCall scheduleCall)
            throws BadRequestException {
       
        if (scheduleCall == null || scheduleCall.getDescription() == null || scheduleCall.getDescription().isEmpty()) {
            throw new BadRequestException("Schedule call description cannot be null or empty");
        }
        return guestAssistanceService.addScheduleCall(scheduleCall);
    }

}
