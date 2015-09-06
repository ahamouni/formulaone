package com.formulaone.controller.dto.merchant;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.formulaone.domain.merchant.Merchant;

public class AddressRequest {

	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	private String address1;
	
	private String address2;
	
	@NotEmpty
	@Size(max = 30)
	private String city;
	
	@NotEmpty
	@Size(max = 30)
	private String state;
	
	@NotEmpty
	@Size(max = 20)
	private String zipCode;

	public AddressRequest() {
		super();
	}

	public AddressRequest(String address1, String address2, String city, String state,
			String zipCode) {

		super();
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return String.format(
				"Address [address1=%s, address2=%s, city=%s, state=%s, zipCode=%s]",
				address1, address2, city, state, zipCode);
	}

}
