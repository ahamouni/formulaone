package com.formulaone.controller.exceptions;

import org.apache.commons.lang3.StringUtils;

public class UserNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = -9069811051722575716L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(Long id) {
		super();
		setId(id);
	}

	public UserNotFoundException(String name) {
		super();
		setName(name);
	}

	protected String getSpecificMessage() {
		if (this.getId() != null) {
			return (String.format("User with the id=%s was not found", this.getId()));
		} else if(StringUtils.isNotBlank(this.getName())) {
			return (String.format("User with the name=%s was not found", this.getName()));
		}
		
		return "";
	}

}
