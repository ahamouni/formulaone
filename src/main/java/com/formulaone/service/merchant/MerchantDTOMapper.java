package com.formulaone.service.merchant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.domain.merchant.Merchant;
import com.formulaone.domain.security.Role;
import com.formulaone.domain.security.UserCredentials;

/**
 * Maps Merchant entity to MerchantResponse DTO
 */
public class MerchantDTOMapper {

	public static List<MerchantResponse> map(List<Merchant> merchants) {
		List<MerchantResponse> responses = new ArrayList<>();
		for (Merchant merchant : merchants) {
			responses.add(map(merchant));
		}
		return responses;
	}

	public static MerchantResponse map(Merchant merchant) {
		return new MerchantResponse(merchant.getId(), merchant.getMid(),
				merchant.getCompanyDetails().getName(),
				merchant.getOwnershipDetails().getFirstName(),
				merchant.getOwnershipDetails().getLastName(),
				merchant.getOwnershipDetails().getMiddleName(),
				merchant.getMid().toString(), merchant.getStatus(),
				merchant.getBusinessDesc(), merchant.getRequestId());
	}

}
