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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.formulaone.domain.BaseAuditingEntity;

@Entity
@Table(name = "ownershipdetails")
public class OwnershipDetails extends BaseAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "firstname", length = 50, nullable = false, unique = false)
	@Size(max = 50)
	private String firstName;

	@Column(name = "middlename", length = 50, nullable = true, unique = false)
	@Size(max = 50)
	private String middleName;

	@Column(name = "lastname", length = 50, nullable = false, unique = false)
	@Size(max = 50)
	private String lastName;

	@Column(name = "dob", nullable = false, unique = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime dob;

	@Column(name = "position", length = 50, nullable = true, unique = false)
	@Size(max = 50)
	private String position;

	@Column(name = "ssn", length = 20, nullable = false, unique = true)
	@Size(max = 20)
	private String ssn;

	@Column(name = "driverlicense", length = 50, nullable = false, unique = true)
	@Size(max = 50)
	private String driverlicense;

	@Column(name = "taxid", length = 50, nullable = false, unique = true)
	@Size(max = 50)
	private String taxiId;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bankingDetails_id")
	private BankingDetails bankingDetails;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "ownershipDetails")
	private Merchant merchant;

	public OwnershipDetails() {
		super();
	}

	public OwnershipDetails(String firstName, String middleName,
			String lastName, DateTime dob, String position, String ssn,
			String driverlicense, String taxiId, Merchant merchant) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dob = dob;
		this.position = position;
		this.ssn = ssn;
		this.driverlicense = driverlicense;
		this.taxiId = taxiId;
		this.merchant = merchant;
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

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	@Override
	public String toString() {
		return String.format(
				"OwnershipDetails [id=%s, firstName=%s, middleName=%s, lastName=%s, dob=%s, position=%s, ssn=%s, driverlicense=%s, taxiId=%s, bankingDetails=%s, merchant=%s]",
				id, firstName, middleName, lastName, dob, position, ssn,
				driverlicense, taxiId, bankingDetails, merchant);
	}

}
