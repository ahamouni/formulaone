package com.formulaone.domain.merchant;

import com.formulaone.controller.dto.merchant.AddressRequest;
import com.formulaone.controller.dto.merchant.CompanyRequest;

public class CompanyDetailsEntityBuilder {

	public static CompanyDetails build(CompanyRequest company,
			Merchant merchant) {
		return buildCompanyDetails(company, merchant);
	}

	private static CompanyDetails buildCompanyDetails(
			CompanyRequest companyRequest, Merchant merchant) {

		CompanyDetails companyDetails = new CompanyDetails();
		companyDetails.setName(companyRequest.getName());
		companyDetails.setPhone(companyRequest.getPhone());
		companyDetails.setAddress(buildAddress(companyRequest.getAddress()));
		companyDetails.setMerchant(merchant);
		Address address = buildAddress(companyRequest.getAddress());
		address.setCompanyDetails(companyDetails);
		companyDetails.setAddress(address);
		return companyDetails;
	}

	private static Address buildAddress(AddressRequest addressRequest) {
		Address address = new Address();
		address.setAddress1(addressRequest.getAddress());
		address.setAddress2(addressRequest.getAddress2());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setZipCode(addressRequest.getZipCode());
		address.setCountry(addressRequest.getCountry());
		return address;
	}

}
