package com.formulaone.controller.dto.merchant;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.formulaone.domain.merchant.Merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AddressRequest", description = "Merchant address resource representation")
public class AddressRequest {

	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	private String address;

	// Optional
	@Size(max = Merchant.MAX_LENGTH_NAME)
	private String address2;

	@NotEmpty
	@Size(max = 30)
	@ApiModelProperty(value = "city", required = true)
	private String city;

	@Size(max = 2)
	@ApiModelProperty(value = "state", required = false)
	private String state;

	@NotEmpty
	@Size(max = 20)
	@ApiModelProperty(value = "zip code", required = true)
	private String zipCode;

	@NotEmpty
	@Size(max = 2)
	@ApiModelProperty(value = "country", required = true)
	private String country;

	public AddressRequest() {
		super();
	}

	public AddressRequest(String address, String address2, String city,
			String state, String zipCode, String country) {

		super();
		this.address = address;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}

	@ApiModelProperty(value = "address", required = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ApiModelProperty(value = "address2", required = false)
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return String.format(
				"AddressRequest [address=%s, address2=%s, city=%s, state=%s, zipCode=%s, country=%s]",
				address, address2, city, state, zipCode, country);
	}

}
