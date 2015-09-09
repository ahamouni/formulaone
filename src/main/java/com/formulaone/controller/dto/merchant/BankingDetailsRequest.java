package com.formulaone.controller.dto.merchant;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value = "BankingDetailsRequest", description = "Banking details resource representation")
public class BankingDetailsRequest {

	@NotEmpty
	@Size(max=20)
	@ApiModelProperty(value = "bank account number", required = true)
	private String bankAccountNumber;
	
	@NotEmpty
	@Size(max=9)
	@ApiModelProperty(value = "routingnumber", required = false)
	private String routingNumber;

	public BankingDetailsRequest() {
		super();
	}

	/**
	 * 
	 * @param bankAccountNumber
	 * @param routingNumber
	 */
	public BankingDetailsRequest(String bankAccountNumber, String routingNumber) {
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
