package com.formulaone.controller.dto.security.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SSNValidator {

	protected abstract String getPatternExpression();

	protected abstract boolean isValidSSN(String ssn);

	public boolean isValid(String ssn) {

		if (!regexCheck(ssn)) {
			return false;
		}

		return isValidSSN(ssn);
	}

	/**
	 * Check through Regex it is a 9-digit number
	 * 
	 * @param ssn
	 * @return
	 */
	private boolean regexCheck(String ssn) {
		CharSequence inputStr = ssn;
		Pattern pattern = Pattern.compile(getPatternExpression());
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
