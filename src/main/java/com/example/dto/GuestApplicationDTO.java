package com.example.dto;
 
import java.math.BigDecimal;
import java.util.Date;
 
import com.example.model.Admin;
import com.example.model.ApplicationStatus;
import com.example.model.CreditCard;
 
public class GuestApplicationDTO {

    private Long applicationId;
    private String mobileNumber;
    private String name;
    private String panId;
    private Long aadhaarNumber;
    private String address;
	private Date dob;
    private Integer employmentYears;
    private String companyName;
    private BigDecimal annualIncome;
    private String incomeProofFilePath;
    private String cardType;
    private ApplicationStatus applicationStatus;
    private String aadhaarFilePath;
    private String panFilePath;
    private String signatureFilePath;
    private String photoFilePath;
    private Admin username;
 
 
	public Admin getUsername() {
		return username;
	}
	public void setUsername(Admin username) {
		this.username = username;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}