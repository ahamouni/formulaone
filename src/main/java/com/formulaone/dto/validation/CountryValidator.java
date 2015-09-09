package com.formulaone.dto.validation;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("unused")
public class CountryValidator implements ConstraintValidator<Country, String> {

	@Override
	public void initialize(Country constraintAnnotation) {
	}

	@Override
	public boolean isValid(String country, ConstraintValidatorContext context) {

		if (Locale.CANADA.getISO3Country().equals(country)
				|| Locale.US.getISO3Country().equals(country)) {
			return true;
		}

		return false;
	}
}
