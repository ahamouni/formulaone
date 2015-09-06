package com.formulaone.controller.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transferring error message.
 */
public class RestErrorInfo implements Serializable {

	private static final long serialVersionUID = -2472026446965712212L;

	private String code = "";
	private String status = "";
	private List<String> errors = new ArrayList<>();

	public RestErrorInfo() {
		super();
	}

	/**
	 * 
	 * @param code
	 * @param status
	 * @param messages
	 */
	public RestErrorInfo(String code, String status, List<String> errors) {
		super();
		this.code = code;
		this.status = status;
		this.errors = errors;
	}

	public RestErrorInfo(String code, String status, String error) {
		super();
		this.code = code;
		this.status = status;
		this.errors.add(error);
	}

	public List<String> getMessages() {
		return errors;
	}

	public void setMessages(List<String> messages) {
		this.errors = messages;
	}

	public String getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

}
