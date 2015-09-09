package com.formulaone.controller.dto.merchant;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "GeneralRequest", description = "General request resource representation")
public class GeneralRequest {

	@NotEmpty
	@Size(max = 2)
	@ApiModelProperty(value = "country of incorporation", required = true)
	private String countryOfIncorporation;

	@Size(max = 50)
	@ApiModelProperty(value = "website", required = false)
	private String website;

	@Size(max = 20)
	@ApiModelProperty(value = "annual processing", required = false)
	private String annualProcessing;

	@NotEmpty
	@Size(min = 28, max=250)
	@ApiModelProperty(value = "descriptor", required = true)
	private String descriptor;

	@NotEmpty
	@Size(max = 20)
	@ApiModelProperty(value = "phone", required = true)
	private String phoneNumber;

	@Size(max = 40)
	@ApiModelProperty(value = "business type", required = false)
	private String businessType;

	public GeneralRequest() {
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
	public GeneralRequest(String countryOfIncorporation, String website,
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
