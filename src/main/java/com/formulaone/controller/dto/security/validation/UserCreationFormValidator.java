package com.formulaone.controller.dto.security.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.formulaone.controller.dto.security.UserRequest;
import com.formulaone.service.security.UserManagementService;

@Component
public class UserCreationFormValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserManagementService userService;

	@Autowired
	public UserCreationFormValidator(UserManagementService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.debug("Validating {}", target);
		UserRequest form = (UserRequest) target;
		validatePasswords(errors, form);
		validateNameUnique(errors, form);
	}

	private void validatePasswords(Errors errors, UserRequest form) {
		if (!form.getPassword().equals(form.getPasswordRepeated())) {
			errors.rejectValue("passwordRepeated", "error.passwords.not_match");
		}
	}

	private void validateNameUnique(Errors errors, UserRequest form) {
		if (userService.getUserByName(form.getName()).isPresent()) {
			errors.rejectValue("name", "error.name.exists");
		}
	}
}
