package com.formulaone.controller.exceptions;

public abstract class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7841418511147833318L;

	private Long id;
	
	protected abstract String getSpecificMessage();


	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}
	
	
	public String getErrorMessage() {
		return String.format(getSpecificMessage(), getId());
	}

}
