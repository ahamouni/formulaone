package com.formulaone.controller.dto.merchant;

public class General {

	private String countryOfIncorporation;
	private String website;
	private String annualProcessing;
	private String descriptor;
	private String phoneNumber;
	private String businessType;

	public General() {
		super();
	}

	/**
	 * 
	 * @param countryOfIncorporation
	 * @param website
	 * @param annualProcessing
	 * @param descriptor
	 * @param phoneNumber
	 * @param businessType
	 */
	public General(String countryOfIncorporation, String website,
			String annualProcessing, String descriptor, String phoneNumber,
			String businessType) {
		super();
		this.countryOfIncorporation = countryOfIncorporation;
		this.website = website;
		this.annualProcessing = annualProcessing;
		this.descriptor = descriptor;
		this.phoneNumber = phoneNumber;
		this.businessType = businessType;
	}

	public String getCountryOfIncorporation() {
		return countryOfIncorporation;
	}

	public void setCountryOfIncorporation(String countryOfIncorporation) {
		this.countryOfIncorporation = countryOfIncorporation;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAnnualProcessing() {
		return annualProcessing;
	}

	public void setAnnualProcessing(String annualProcessing) {
		this.annualProcessing = annualProcessing;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Override
	public String toString() {
		return String.format(
				"General [countryOfIncorporation=%s, website=%s, annualProcessing=%s, descriptor=%s, phoneNumber=%s, businessType=%s]",
				countryOfIncorporation, website, annualProcessing, descriptor,
				phoneNumber, businessType);
	}

}
