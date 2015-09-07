package com.formulaone.controller.exceptions;

public abstract class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7841418511147833318L;

	protected abstract String getSpecificMessage();

	private Long id;

	private String name;

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrorMessage() {
		return getSpecificMessage();
	}

}
