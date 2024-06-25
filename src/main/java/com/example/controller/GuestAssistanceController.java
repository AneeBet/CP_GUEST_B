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
	public GuestFeedback createFeedback(@RequestBody GuestFeedback feedback) {
		return guestAssistanceService.createFeedback(feedback);
	}

	@GetMapping("/grievance/readall/{guestId}")
	public List<GuestGrievance> getAllGrievances(@PathVariable("guestId") String guestId) {
		return guestAssistanceService.getAllGrievances(guestId);
	}

	@PostMapping("/grievance/add")
	public GuestGrievance createGrievance( @RequestBody GuestGrievance grievance) throws BadRequestException {
		return guestAssistanceService.createGrievance(grievance);
	}

	@PostMapping("/schedulecall/add")
	public GuestScheduleCall addScheduleCall( @RequestBody GuestScheduleCall scheduleCall)
			throws BadRequestException {
		return guestAssistanceService.addScheduleCall(scheduleCall);
	}

}
