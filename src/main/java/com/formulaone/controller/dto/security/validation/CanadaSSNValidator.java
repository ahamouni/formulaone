package com.formulaone.controller.dto.security.validation;

public class CanadaSSNValidator extends SSNValidator {

	protected String getPatternExpression() {
		return "^\\d{3}[- ]?\\d{3}[- ]?\\d{3}$";
	}

	protected boolean isValidSSN(final String ssn) {
		// trip all non numeric characters
		String digits = ssn.replaceAll("[^0-9]", "");
		int iChecksum = 0;
		int iDigit = 0;

		for (int i = 0; i < digits.length(); i++) {
			// even number else odd
			if (((i + 1) % 2) == 0) {
				iDigit = Integer.parseInt(digits.substring(i, i + 1)) * 2;
				iChecksum += (iDigit < 10) ? iDigit : iDigit - 9;
			} else {
				iChecksum += Integer.parseInt(digits.substring(i, i + 1));
			}
		}

		return ((iChecksum % 10) == 0) ? true : false;
	}

}
