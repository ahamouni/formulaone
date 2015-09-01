package com.formulaone.controller.dto.merchant;

import org.joda.money.Money;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.formulaone.jason.MoneyDeserializer;
import com.formulaone.jason.MoneySerializer;

/**
 * This DTO class represents the on boarding merchant request
 */
public class MerchantRequest {
	private String name;
	private String email;
	private String legalName;

	// Optional
	private String businessDescription;

	// Optional
	private Money averagePaymentAmount;

	private Company company;
	private OwnershipDetails ownershipDetails;
	private BankingDetails bankingDetails;
	private General general;

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
	public MerchantRequest(String name, String email, String legalName, String businessDescription,
			Money averagePaymentAmount, Company company, OwnershipDetails ownershipDetails,
			BankingDetails bankingDetails, General general) {
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

	@JsonSerialize(using = MoneySerializer.class)
	public Money getAveragePaymentAmount() {
		return averagePaymentAmount;
	}

	@JsonDeserialize(using = MoneyDeserializer.class)
	public void setAveragePaymentAmount(Money averagePaymentAmount) {
		this.averagePaymentAmount = averagePaymentAmount;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public OwnershipDetails getOwnershipDetails() {
		return ownershipDetails;
	}

	public void setOwnershipDetails(OwnershipDetails ownershipDetails) {
		this.ownershipDetails = ownershipDetails;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public General getGeneral() {
		return general;
	}

	public void setGeneral(General general) {
		this.general = general;
	}

	@Override
	public String toString() {
		return String.format(
				"MerchantRequest [name=%s, email=%s, legalName=%s, company=%s, ownershipDetails=%s, bankingDetails=%s, general=%s, businessDescription=%s, averagePaymentAmount=%s]",
				name, email, legalName, company, ownershipDetails, bankingDetails, general, businessDescription,
				averagePaymentAmount);
	}

}
