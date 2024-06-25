package com.example.dto;

public class GuestSignupDTO {
	public GuestSignupDTO() {
		super();
	}

	private String name;

	private String email;

	private String password;

	public String getName() {
		return name;
	}

	

	public String getEmail() {
		return email;
	}

	public void setGuestEmail(String email) {
		this.email = email;
	}

	public GuestSignupDTO(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

}
