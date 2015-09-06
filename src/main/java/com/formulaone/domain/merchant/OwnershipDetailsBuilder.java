package com.formulaone.domain.merchant;

import com.formulaone.controller.dto.merchant.BankingDetailsRequest;
import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.OwnershipDetailsRequest;

public class OwnershipDetailsBuilder {

	public static OwnershipDetails build(MerchantRequest request) {
		OwnershipDetails ownershipDetails = new OwnershipDetails();
		OwnershipDetailsRequest ownershipDetailsRequest = request
				.getOwnershipDetails();
		ownershipDetails.setDob(ownershipDetailsRequest.getDob());
		ownershipDetails
				.setDriverlicense(ownershipDetailsRequest.getDriverlicense());
		ownershipDetails.setFirstName(ownershipDetailsRequest.getFirstName());
		ownershipDetails.setLastName(ownershipDetailsRequest.getLastName());
		ownershipDetails.setMiddleName(ownershipDetailsRequest.getMiddleName());
		ownershipDetails.setPosition(ownershipDetailsRequest.getPosition());
		ownershipDetails.setSsn(ownershipDetailsRequest.getSsn());
		ownershipDetails.setTaxiId(ownershipDetailsRequest.getTaxiId());

		BankingDetails bankingDetails = buildBankingDetails(
				request.getBankingDetails());

		bankingDetails.setOwnershipDetails(ownershipDetails);
		ownershipDetails.setBankingDetails(bankingDetails);
		return ownershipDetails;
	}

	private static BankingDetails buildBankingDetails(
			BankingDetailsRequest bankingDetailsRequest) {
		BankingDetails details = new BankingDetails();
		details.setBankAccountNumber(
				bankingDetailsRequest.getBankAccountNumber());
		details.setRoutingNumber(bankingDetailsRequest.getRoutingNumber());
		return details;
	}

}
