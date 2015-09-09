package com.formulaone.dto.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class StateProvinceValidator
		implements ConstraintValidator<StateProvince, String> {

	private final static String provinces[] = new String[] { "AB", "BC", "MB",
			"NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT" };
	private final static String states[] = new String[] { "AL", "AK", "AZ",
			"AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL",
			"IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
			"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
			"OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "VI", "UT",
			"VT", "VA", "WA", "WV", "WI", "WY", };

	private String country;

	@Override
	public void initialize(StateProvince constraintAnnotation) {
		this.country = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String state, ConstraintValidatorContext context) {

		if (StringUtils.isBlank(state)) {
			return true;
		}

		if (Locale.CANADA.getISO3Country().equalsIgnoreCase(country)) {
			return Arrays.asList(provinces).contains(states);
		} else if (Locale.US.getISO3Country().equalsIgnoreCase(country)) {
			return Arrays.asList(states).contains(states);
		}
		return false;
	}
}
