package com.formulaone.controller.dto.merchant.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.formulaone.controller.dto.merchant.MerchantRequest;

@Component
public class MerchantValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public boolean supports(Class<?> clazz) {
		 return MerchantRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.debug("Validating {}", target);
	}

}
