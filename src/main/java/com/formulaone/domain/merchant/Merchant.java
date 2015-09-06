package com.formulaone.domain.merchant;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.domain.BaseAuditingEntity;

@Entity
@Table(name = "merchant")
public class Merchant extends BaseAuditingEntity {

	public static final int MAX_LENGTH_NAME = 50;

	@Id
	@Column(name = "mid", nullable = false, updatable = false)
	private Long mid;

	@Column(name = "name", length = 50, nullable = false, unique = false)
	@Size(max = 50)
	private String name;

	@Column(name = "email", length = 50, nullable = false, unique = false)
	@Size(max = 50)
	private String email;

	@Column(name = "legalname", length = 50, nullable = false, unique = false)
	@Size(max = 50)
	private String legalName;

	@Column(name = "description", length = 250, nullable = true, unique = false)
	@Size(max = 50)
	private String businessDesc;

	@Column(name = "avgPaymentAmount", nullable = true, unique = false)
	private Long avgPaymentAmount;

	@Column(name = "requestid", nullable = true, unique = true)
	private Long requestId;

	@Column(name = "status", nullable = false, unique = false)
	private String status;;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "company_id")
	private CompanyDetails companyDetails;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ownershipdetails_id")
	private OwnershipDetails ownershipDetails;

	public Merchant() {
		super();
	}

	private Merchant(MerchantEntityBuilder builder) {
		if (builder.merchantRequest.getMid() != null) {
			this.mid = builder.merchantRequest.getMid();
		}

		this.name = builder.merchantRequest.getName();
		this.legalName = builder.merchantRequest.getLegalName();
		this.email = builder.merchantRequest.getEmail();
		this.businessDesc = builder.merchantRequest.getBusinessDescription();

		// to remove
		this.status = VerificationStatus.PENDING.toString();

		// Convert Bigdecimal to Long since money is stored in cents
		BigDecimal amount = builder.merchantRequest.getAveragePaymentAmount();
		if (amount != null) {
			this.avgPaymentAmount = amount.movePointRight(2).longValue();
		}
		this.companyDetails = CompanyDetailsEntityBuilder
				.build(builder.merchantRequest.getCompany(), this);
		this.ownershipDetails = OwnershipDetailsBuilder
				.build(builder.merchantRequest);

	}

	public Long getMid() {
		return mid;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getLegalName() {
		return legalName;
	}

	public String getBusinessDesc() {
		return businessDesc;
	}

	public Long getAvgPaymentAmount() {
		return avgPaymentAmount;
	}

	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public OwnershipDetails getOwnershipDetails() {
		return ownershipDetails;
	}

	public Long getRequestId() {
		return requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}

	public void setAvgPaymentAmount(Long avgPaymentAmount) {
		this.avgPaymentAmount = avgPaymentAmount;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

	public void setOwnershipDetails(OwnershipDetails ownershipDetails) {
		this.ownershipDetails = ownershipDetails;
	}

	@Override
	public String toString() {
		return String.format(
				"Merchant [mid=%s, name=%s, email=%s, legalName=%s, businessDesc=%s, avgPaymentAmount=%s, companyDetails=%s, ownershipDetails=%s]",
				mid, name, email, legalName, businessDesc, avgPaymentAmount,
				companyDetails, ownershipDetails);
	}

	public static class MerchantEntityBuilder {
		private MerchantRequest merchantRequest;

		public MerchantEntityBuilder(MerchantRequest merchantRequest) {
			this.merchantRequest = merchantRequest;
		}

		public Merchant build() {
			return new Merchant(this);
		}
	}

}
