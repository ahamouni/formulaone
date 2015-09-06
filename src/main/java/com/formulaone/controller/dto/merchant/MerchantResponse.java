package com.formulaone.controller.dto.merchant;

/**
 * This DTO class represents the on merchant onboarding status
 */
public class MerchantResponse {

	private String companyName;

	// First+Middle+Last Name
	private String firstName;
	private String lastName;
	private String middle;

	private String TerminalId;

	// Status – Approved, Declined, Pending
	private String status;

	private String description;

	// Merchant request identifier
	private Long requestId;

	private Long mid;

	public MerchantResponse() {
		super();
	}

	/**
	 * 
	 * @param companyName
	 * @param firstName
	 * @param lastName
	 * @param middle
	 * @param terminalId
	 * @param status
	 * @param description
	 * @param requestId
	 */
	public MerchantResponse(Long mid, String companyName, String firstName,
			String lastName, String middle, String terminalId, String status,
			String description, Long requestId) {
		super();
		this.mid = mid;
		this.companyName = companyName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middle = middle;
		TerminalId = terminalId;
		this.status = status;
		this.description = description;
		this.requestId = requestId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getTerminalId() {
		return TerminalId;
	}

	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return String.format(
				"MerchantResponse [companyName=%s, firstName=%s, lastName=%s, middle=%s, TerminalId=%s, status=%s, description=%s, requestId=%s, mid=%s]",
				companyName, firstName, lastName, middle, TerminalId, status,
				description, requestId, mid);
	}

}
