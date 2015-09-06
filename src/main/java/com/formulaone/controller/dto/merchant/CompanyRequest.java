package com.formulaone.controller.dto.merchant;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.formulaone.domain.merchant.Merchant;

public class CompanyRequest {

	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	private String name;
	
	@NotEmpty
	@Size(max = Merchant.MAX_LENGTH_NAME)
	private String phone;
	
	@Valid
	private AddressRequest address;

	public CompanyRequest() {
		super();
	}

	public CompanyRequest(String name, String phone, AddressRequest address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public CompanyRequest(String name, AddressRequest address) {
		super();
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressRequest getAddress() {
		return address;
	}

	public void setAddress(AddressRequest address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return String.format("Company [name=%s, phone=%s, address=%s]", name,
				phone, address);
	}

}
