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
@Table(name = "bankingdetails")
public class BankingDetails extends BaseAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANKINGDETAILS_ID_SEQ")
	@SequenceGenerator(name = "BANKINGDETAILS_ID_SEQ", sequenceName = "BANKINGDETAILS_ID_SEQ", allocationSize = 1)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "accountnumber", length = 20, nullable = true, unique = false)
	private String bankAccountNumber;

	@Column(name = "routingnumber", length = 9, nullable = false, unique = false)
	private String routingNumber;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "bankingDetails")
	private OwnershipDetails ownershipDetails;

	public BankingDetails() {
		super();
	}

	/**
	 * 
	 * @param bankAccountNumber
	 * @param routingNumber
	 * @param ownershipDetails
	 */
	public BankingDetails(String bankAccountNumber, String routingNumber,
			OwnershipDetails ownershipDetails) {
		super();
		this.bankAccountNumber = bankAccountNumber;
		this.routingNumber = routingNumber;
		this.ownershipDetails = ownershipDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public OwnershipDetails getOwnershipDetails() {
		return ownershipDetails;
	}

	public void setOwnershipDetails(OwnershipDetails ownershipDetails) {
		this.ownershipDetails = ownershipDetails;
	}

	@Override
	public String toString() {
		return String.format(
				"BankingDetails [id=%s, bankAccountNumber=%s, routingNumber=%s, ownershipDetails=%s]",
				id, bankAccountNumber, routingNumber, ownershipDetails);
	}

}