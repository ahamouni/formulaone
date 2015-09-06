package com.formulaone.controller.exceptions;

public class MerchantNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = -9069811051722575716L;

	public MerchantNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MerchantNotFoundException(Long id) {
		super();
		setId(id);
	}

	protected String getSpecificMessage() {
		return ("Merchant with the id=%s was not found");
	}

}
