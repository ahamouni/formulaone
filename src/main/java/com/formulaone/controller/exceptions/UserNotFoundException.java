package com.formulaone.controller.exceptions;

public class UserNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = -9069811051722575716L;
	
	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(Long id) {
		super();
		setId(id);
	}

	protected String getSpecificMessage() {
		return ("User with the id=%s was not found");
	}

}
