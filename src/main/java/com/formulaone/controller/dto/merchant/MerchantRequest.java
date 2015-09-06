package com.formulaone.controller.dto.merchant;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.formulaone.domain.merchant.Merchant;
import com.formulaone.domain.security.UserCredentials;

/**
 * This DTO class represents the on boarding merchant request
 */
public class MerchantRequest {

	private Long mid;

	@NotEmpty()
	@Size(max = Merchant.MAX_LENGTH_NAME)
	private String name;
	
	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	@Email
	private String email;
	
	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	private String legalName;

	// Optional
	private String businessDescription;

	// Optional
	private BigDecimal averagePaymentAmount;

	@Valid
	private CompanyRequest company;
	
	@Valid
	private OwnershipDetailsRequest ownershipDetails;
	
	@Valid
	private BankingDetailsRequest bankingDetails;
	
	@Valid
	private GeneralRequest general;

	public MerchantRequest() {
		super();
	}

	/**
	 * 
	 * @param name
	 * @param email
	 * @param legalName
	 * @param businessDescription
	 * @param averagePaymentAmount
	 * @param company
	 * @param ownershipDetails
	 * @param bankingDetails
	 * @param general
	 */
	public MerchantRequest(String name, String email, String legalName,
			String businessDescription, BigDecimal averagePaymentAmount,
			CompanyRequest company, OwnershipDetailsRequest ownershipDetails,
			BankingDetailsRequest bankingDetails, GeneralRequest general) {
		super();
		this.name = name;
		this.email = email;
		this.legalName = legalName;
		this.businessDescription = businessDescription;
		this.averagePaymentAmount = averagePaymentAmount;
		this.company = company;
		this.ownershipDetails = ownershipDetails;
		this.bankingDetails = bankingDetails;
		this.general = general;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getBusinessDescription() {
		return businessDescription;
	}

	public void setBusinessDescription(String businessDescription) {
		this.businessDescription = businessDescription;
	}

	public BigDecimal getAveragePaymentAmount() {
		return averagePaymentAmount;
	}

	public void setAveragePaymentAmount(BigDecimal averagePaymentAmount) {
		this.averagePaymentAmount = averagePaymentAmount;
	}

	public CompanyRequest getCompany() {
		return company;
	}

	public void setCompany(CompanyRequest company) {
		this.company = company;
	}

	public OwnershipDetailsRequest getOwnershipDetails() {
		return ownershipDetails;
	}

	public void setOwnershipDetails(OwnershipDetailsRequest ownershipDetails) {
		this.ownershipDetails = ownershipDetails;
	}

	public BankingDetailsRequest getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetailsRequest bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public GeneralRequest getGeneral() {
		return general;
	}

	public void setGeneral(GeneralRequest general) {
		this.general = general;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return String.format(
				"MerchantRequest [name=%s, email=%s, legalName=%s, company=%s, ownershipDetails=%s, bankingDetails=%s, general=%s, businessDescription=%s, averagePaymentAmount=%s]",
				name, email, legalName, company, ownershipDetails,
				bankingDetails, general, businessDescription,
				averagePaymentAmount);
	}

}
