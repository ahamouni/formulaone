package com.formulaone.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.formulaone.domain.BaseAuditingEntity;

@Entity
@Table(name = "address")
public class Address extends BaseAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_ID_SEQ")
	@SequenceGenerator(name = "ADDRESS_ID_SEQ", sequenceName = "ADDRESS_ID_SEQ", allocationSize = 1)

	private Long id;

	@Column(name = "address1", length = 50, nullable = false, unique = false)
	private String address1;

	@Column(name = "address2", length = 50, nullable = true, unique = false)
	private String address2;

	@Column(name = "city", length = 30, nullable = false, unique = false)
	private String city;

	@Column(name = "state", length = 2, nullable = true, unique = false)
	private String state;

	@Column(name = "zipcode", length = 20, nullable = false, unique = false)
	private String zipCode;

	@Column(name = "country", length = 2, nullable = false, unique = false)
	private String country;

	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "address")
	private CompanyDetails companyDetails;

	public Address() {
		super();
	}

	/**
	 * 
	 * @param address1
	 * @param address2
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 * @param companyDetails
	 */
	public Address(String address1, String address2, String city, String state,
			String zipCode, String country, CompanyDetails companyDetails) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
		this.companyDetails = companyDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

	@Override
	public String toString() {
		return String.format(
				"Address [id=%s, address1=%s, address2=%s, city=%s, state=%s, zipCode=%s, country=%s, companyDetails=%s]",
				id, address1, address2, city, state, zipCode, country,
				companyDetails);
	}

}
