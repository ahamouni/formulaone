package com.formulaone.controller.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9069811051722575716L;

	public static final String CODE = "error.user.notfound";

	private Long id;
 
	public UserNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(Long id) {
		super();
		setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
