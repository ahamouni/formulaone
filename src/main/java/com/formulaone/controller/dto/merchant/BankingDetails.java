package com.formulaone.controller.dto.merchant;

/**
 * Payment details information for both US and Canada. 
 * For Usa: 
 * - Bank account number: 10-12 digits 
 * - Routing number: 9 digits
 * 
 * for Canada 
 * - Bank account number: 10-12 digits 
 * - Routing number is the concatenation of: 
 *     - Transit number: 5digits 
 *     - Institution number: 3 digits
 */
public class BankingDetails {

	private String bankAccountNumber;
	private String routingNumber;

	public BankingDetails() {
		super();
	}

	/**
	 * 
	 * @param bankAccountNumber
	 * @param routingNumber
	 */
	public BankingDetails(String bankAccountNumber, String routingNumber) {
		super();
		this.bankAccountNumber = bankAccountNumber;
		this.routingNumber = routingNumber;
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

	@Override
	public String toString() {
		return String.format("BankingDetails [bankAccountNumber=%s, routingNumber=%s]", 
				bankAccountNumber, routingNumber);
	}

}
