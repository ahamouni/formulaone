package com.formulaone.controller.dto.security.validation;

public class USSSNValidator extends SSNValidator {

	protected String getPatternExpression() {
		return "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";
	}

	@Override
	protected boolean isValidSSN(String ssn) {
		return true;
	}

}
