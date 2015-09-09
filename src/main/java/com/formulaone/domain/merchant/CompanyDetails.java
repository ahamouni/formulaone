package com.formulaone.domain.merchant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.formulaone.domain.BaseAuditingEntity;

@Entity
@Table(name = "companydetails")
public class CompanyDetails extends BaseAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANYDETAILS_ID_SEQ")
	@SequenceGenerator(name = "COMPANYDETAILS_ID_SEQ", sequenceName = "COMPANYDETAILS_ID_SEQ", allocationSize = 1)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "name", length = 50, nullable = false, unique = false)
	@Size(max = 50)
	private String name;

	@Column(name = "phone", length=20, nullable = false, unique = false)
	private String phone;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "companyDetails")
	private Merchant merchant;

	public CompanyDetails() {
		super();
	}

	public CompanyDetails(String name, String phone, Address address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public CompanyDetails(String name, String phone, Address address,
			Merchant merchant) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.merchant = merchant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Override
	public String toString() {
		return String.format(
				"CompanyDetails [id=%s, name=%s, phone=%s, address=%s, merchant=%s]",
				id, name, phone, address, merchant);
	}

}
