package com.formulaone.controller.dto.security.validation;

import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.formulaone.controller.dto.merchant.AddressRequest;
import com.formulaone.controller.dto.merchant.BankingDetailsRequest;
import com.formulaone.controller.dto.merchant.CompanyRequest;
import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.OwnershipDetailsRequest;

@Component
public class MerchantCreationFormValidator implements Validator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String provinces[] = new String[] { "AB", "BC", "MB",
			"NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT" };

	private final static String states[] = new String[] { "AL", "AK", "AZ",
			"AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL",
			"IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
			"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
			"OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "VI", "UT",
			"VT", "VA", "WA", "WV", "WI", "WY", };

	@Override
	public boolean supports(Class<?> clazz) {
		return MerchantRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.debug("Validating {}");
		MerchantRequest request = (MerchantRequest) target;

		validateRequiredBlocksArePresent(request, errors);
		validateCountry(request, errors);
		validateStateProvince(request, errors);
		validateSSN(request, errors);
	}

	private void validateRequiredBlocksArePresent(MerchantRequest request,
			Errors errors) {

		logger.debug("Validationg MerchantRequests blocks");
		BankingDetailsRequest bankingDetails = request.getBankingDetails();
		if (bankingDetails == null) {
			errors.rejectValue("bankingDetails", null,
					"banking details information missing");
		}

		CompanyRequest company = request.getCompany();
		if (company == null) {
			errors.rejectValue("company", null,
					"company details information missing");
		}

		AddressRequest address = company.getAddress();
		if (address == null) {
			errors.rejectValue("company.address", null,
					"Address information missing");
		}

		OwnershipDetailsRequest ownershipDetails = request
				.getOwnershipDetails();
		if (ownershipDetails == null) {
			errors.rejectValue("ownershipDetails", null,
					"OwnerShip details information missing");
		}

		logger.debug("MerchantRequests blocks successfully validated");
	}

	private void validateCountry(MerchantRequest request, Errors errors) {
		logger.debug("Validationg MerchantRequest country");
		String country = request.getCompany().getAddress().getCountry();
		if (StringUtils.isBlank(country)) {
			errors.rejectValue("company.address.country", null,
					"Country must be set to US or CA");
		}

		if (!Locale.CANADA.getCountry().equalsIgnoreCase(country)
				&& !Locale.US.getCountry().equalsIgnoreCase(country)) {

			errors.rejectValue("company.address.country", null,
					"Country must be set to US or CA");
		}

		logger.debug("Country successfully validated");

	}

	private void validateStateProvince(MerchantRequest request, Errors errors) {

		logger.debug("Validationg State/Province");

		String state = request.getCompany().getAddress().getState();
		if (StringUtils.isBlank(state)) {
			return;
		}

		String country = request.getCompany().getAddress().getCountry();
		if (Locale.CANADA.getCountry().equalsIgnoreCase(country)
				&& !Arrays.asList(provinces).contains(state)) {

			errors.rejectValue("company.address.state", null,
					"Province not part of Canada");
		} else if (Locale.US.getCountry().equalsIgnoreCase(country)
				&& !Arrays.asList(states).contains(state)) {

			errors.rejectValue("company.address.state", null,
					"State not part of the US");
		}

		logger.debug("State/Province successfully validated");
	}

	private void validateSSN(MerchantRequest request, Errors errors) {

		SSNValidator ssnValidator;
		String country = request.getCompany().getAddress().getCountry();
		String ssn = request.getOwnershipDetails().getSsn();
		if (StringUtils.isBlank(ssn)) {
			return;
		}

		if (Locale.CANADA.getCountry().equalsIgnoreCase(country)) {
			logger.debug("Processing Canadian ssn");
			ssnValidator = new CanadaSSNValidator();
		} else {
			logger.debug("Processing US ssn");
			ssnValidator = new USSSNValidator();
		}

		boolean res = ssnValidator.isValid(ssn);
		if (!res) {
			errors.rejectValue("ownershipDetails.ssn", null, "Invalid SSN.");
		}

	}

}
