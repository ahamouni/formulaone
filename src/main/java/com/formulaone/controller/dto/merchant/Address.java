package com.formulaone.controller.dto.merchant;

public class Address {

	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipCode;
	private String phone;

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(String address1, String address2, String city, 
			String state, String zipCode, String phone) {
		
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phone = phone;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return String.format("Address [address1=%s, address2=%s, city=%s, state=%s, zipCode=%s, phone=%s]", address1,
				address2, city, state, zipCode, phone);
	}

}
