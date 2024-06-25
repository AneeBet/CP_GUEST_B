package com.example.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "guestProfile")
public class GuestProfile {
	@Id
	@Column(name = "guestEmail")
	@NotNull(message = "Guest email cannot be null")
	@Email(message = "Guest email should be valid")
	private String guestEmail;

	@Column(name = "password", length = 500)
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	@Column(name = "applicationId")
	private Long applicationId;

	@Column(name = "mobileNumber", length = 10)
	@Pattern(regexp = "\\d{10}", message = "Mobile number must be a 10 digit number")
	private String mobileNumber;

	@Column(name = "name", length = 100)
	@Size(max = 100, message = "Name must be less than or equal to 100 characters")
	private String name;

	@Column(name = "panId")
	@Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]", message = "PAN ID must be a valid format")
	private String panId;

	@Column(name = "aadhaarNumber")
	@Pattern(regexp = "\\d{12}", message = "Aadhaar number must be a 12-digit number")
	private Long aadhaarNumber;

	@Column(name = "address", length = 200)
	@Size(max = 200, message = "Address must be less than or equal to 200 characters")
	private String address;

	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	@Past(message = "Date of birth must be in the past")
	private Date dob;

	@Column(name = "employmentYears")
	private Integer employmentYears;


	@Column(name = "companyName", length = 100)
	@Size(max = 100, message = "Company name must be less than or equal to 100 characters")
	private String companyName;

	@Column(name = "annualIncome")
	@DecimalMin(value = "0.0", inclusive = true, message = "Annual income must be greater than or equal to 0")
	private BigDecimal annualIncome;

	@Column(name = "incomeProofFilePath", length = 255)
	@Size(max = 255, message = "Income proof file path must be less than or equal to 255 characters")
	private String incomeProofFilePath;

	@ManyToOne
	@JoinColumn(name = "cardType", referencedColumnName = "cardType")
	private CreditCard creditCard;

	@Enumerated(EnumType.STRING)
	@Column(name = "applicationStatus")
	private ApplicationStatus applicationStatus;

	@Column(name = "aadhaarFilePath", length = 255)
	@Size(max = 255, message = "Aadhaar file path must be less than or equal to 255 characters")
	private String aadhaarFilePath;

	@Column(name = "panFilePath", length = 255)
	@Size(max = 255, message = "PAN file path must be less than or equal to 255 characters")
	private String panFilePath;

	@Column(name = "signatureFilePath", length = 255)
	@Size(max = 255, message = "Signature file path must be less than or equal to 255 characters")
	private String signatureFilePath;

	@Column(name = "photoFilePath", length = 255)
	@Size(max = 255, message = "Photo file path must be less than or equal to 255 characters")
	private String photoFilePath;
	
	@ManyToOne
	@JoinColumn(name = "assignedAdmin", referencedColumnName = "username")
	private Admin admin;

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPanId() {
		return panId;
	}

	public void setPanId(String panId) {
		this.panId = panId;
	}

	public Long getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(Long aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getEmploymentYears() {
		return employmentYears;
	}

	public void setEmploymentYears(Integer employmentYears) {
		this.employmentYears = employmentYears;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getIncomeProofFilePath() {
		return incomeProofFilePath;
	}

	public void setIncomeProofFilePath(String incomeProofFilePath) {
		this.incomeProofFilePath = incomeProofFilePath;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getAadhaarFilePath() {
		return aadhaarFilePath;
	}

	public void setAadhaarFilePath(String aadhaarFilePath) {
		this.aadhaarFilePath = aadhaarFilePath;
	}

	public String getPanFilePath() {
		return panFilePath;
	}

	public void setPanFilePath(String panFilePath) {
		this.panFilePath = panFilePath;
	}

	public String getSignatureFilePath() {
		return signatureFilePath;
	}

	public void setSignatureFilePath(String signatureFilePath) {
		this.signatureFilePath = signatureFilePath;
	}

	public String getPhotoFilePath() {
		return photoFilePath;
	}

	public void setPhotoFilePath(String photoFilePath) {
		this.photoFilePath = photoFilePath;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public GuestProfile(
			@NotNull(message = "Guest email cannot be null") @Email(message = "Guest email should be valid") String guestEmail,
			@Size(min = 6, message = "Password must be at least 6 characters") String password, Long applicationId,
			@Pattern(regexp = "\\d{10}", message = "Mobile number must be a 10 digit number") String mobileNumber,
			@Size(max = 100, message = "Name must be less than or equal to 100 characters") String name,
			@Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]", message = "PAN ID must be a valid format") String panId,
			@Pattern(regexp = "\\d{12}", message = "Aadhaar number must be a 12-digit number") Long aadhaarNumber,
			@Size(max = 200, message = "Address must be less than or equal to 200 characters") String address,
			@Past(message = "Date of birth must be in the past") Date dob, Integer employmentYears,
			@Size(max = 100, message = "Company name must be less than or equal to 100 characters") String companyName,
			@DecimalMin(value = "0.0", inclusive = true, message = "Annual income must be greater than or equal to 0") BigDecimal annualIncome,
			@Size(max = 255, message = "Income proof file path must be less than or equal to 255 characters") String incomeProofFilePath,
			CreditCard creditCard, ApplicationStatus applicationStatus,
			@Size(max = 255, message = "Aadhaar file path must be less than or equal to 255 characters") String aadhaarFilePath,
			@Size(max = 255, message = "PAN file path must be less than or equal to 255 characters") String panFilePath,
			@Size(max = 255, message = "Signature file path must be less than or equal to 255 characters") String signatureFilePath,
			@Size(max = 255, message = "Photo file path must be less than or equal to 255 characters") String photoFilePath,
			Admin admin) {
		super();
		this.guestEmail = guestEmail;
		this.password = password;
		this.applicationId = applicationId;
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.panId = panId;
		this.aadhaarNumber = aadhaarNumber;
		this.address = address;
		this.dob = dob;
		this.employmentYears = employmentYears;
		this.companyName = companyName;
		this.annualIncome = annualIncome;
		this.incomeProofFilePath = incomeProofFilePath;
		this.creditCard = creditCard;
		this.applicationStatus = applicationStatus;
		this.aadhaarFilePath = aadhaarFilePath;
		this.panFilePath = panFilePath;
		this.signatureFilePath = signatureFilePath;
		this.photoFilePath = photoFilePath;
		this.admin = admin;
	}

	public GuestProfile() {
		super();
	}

	
	

}
