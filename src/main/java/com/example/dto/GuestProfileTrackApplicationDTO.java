package com.example.dto;

import com.example.model.ApplicationStatus;

public class GuestProfileTrackApplicationDTO {
	private String name;
	private String cardType;
	private Long applicationId;
	private ApplicationStatus applicationStatus;



	public GuestProfileTrackApplicationDTO(String name, String cardType, Long applicationId,
			ApplicationStatus applicationStatus) {
		super();
		this.name = name;
		this.cardType = cardType;
		this.applicationId = applicationId;
		this.applicationStatus = applicationStatus;
	}

	public GuestProfileTrackApplicationDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
