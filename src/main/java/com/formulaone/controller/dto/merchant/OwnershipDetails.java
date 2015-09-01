package com.formulaone.controller.dto.merchant;

import org.joda.time.DateTime;

public class OwnershipDetails {

	private String firstName;
	private String middleName;
	private String lastName;
	private DateTime dob;
	private String position;
	private String ssn;
	private String driverlicense;
	private String taxiId;

	public OwnershipDetails() {
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
	public OwnershipDetails(String firstName, String middleName, String lastName, DateTime dob, String position,
			String ssn, String driverlicense, String taxiId) {
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
				firstName, middleName, lastName, dob, position, ssn, driverlicense, taxiId);
	}

}
