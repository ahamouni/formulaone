package com.formulaone.controller.dto.merchant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.formulaone.domain.merchant.Merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "OwnershipDetailsRequest", description = "Ownership details  resource representation")
public class OwnershipDetailsRequest {

	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	@ApiModelProperty(value = "merchant's firstname", required = true)
	private String firstName;

	@Size(max = Merchant.MAX_LENGTH_NAME)
	@ApiModelProperty(value = "merchant's middle name", required = false)
	private String middleName;

	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	@ApiModelProperty(value = "merchant's last name", required = true)
	private String lastName;

	@NotNull
	@Past
	@ApiModelProperty(value = "merchant's date of birth", required = true)
	private DateTime dob;

	@Size(max = Merchant.MAX_LENGTH_NAME)
	@ApiModelProperty(value = "merchant's position", required = false)
	private String position;

	@NotEmpty
	@Size(max = 20)
	@ApiModelProperty(value = "merchant's ssn", required = true)
	private String ssn;

	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	@ApiModelProperty(value = "merchant's driver license", required = false)
	private String driverlicense;

	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	@ApiModelProperty(value = "merchant's taxi Id", required = true)
	private String taxiId;

	public OwnershipDetailsRequest() {
		super();
	}

	/**
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param dob
	 * @param position
	 * @param ssn
	 * @param driverlicense
	 * @param taxiId
	 */
	public OwnershipDetailsRequest(String firstName, String middleName,
			String lastName, DateTime dob, String position, String ssn,
			String driverlicense, String taxiId) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dob = dob;
		this.position = position;
		this.ssn = ssn;
		this.driverlicense = driverlicense;
		this.taxiId = taxiId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public DateTime getDob() {
		return dob;
	}

	public void setDob(DateTime dob) {
		this.dob = dob;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getDriverlicense() {
		return driverlicense;
	}

	public void setDriverlicense(String driverlicense) {
		this.driverlicense = driverlicense;
	}

	public String getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(String taxiId) {
		this.taxiId = taxiId;
	}

	@Override
	public String toString() {
		return String.format(
				"OwnershipDetails [firstName=%s, middleName=%s, lastName=%s, dob=%s, position=%s, ssn=%s, driverlicense=%s, taxiId=%s]",
				firstName, middleName, lastName, dob, position, ssn,
				driverlicense, taxiId);
	}

}
