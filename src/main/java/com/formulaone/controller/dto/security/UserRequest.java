package com.formulaone.controller.dto.security;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.formulaone.domain.security.UserCredentials;

public class UserRequest {

	
	@NotEmpty
	@Size(max=UserCredentials.MAX_LENGTH_NAME)
	private String name;

	@NotEmpty
	@Size(max=UserCredentials.MAX_LENGTH_PASSWORD)
	private String password;

	@NotEmpty
	@Size(max=UserCredentials.MAX_LENGTH_PASSWORD)
	private String passwordRepeated;

	@NotEmpty
	private Set<String> roles = new HashSet<>();

	
	private Long id;

	public UserRequest() {
		super();
	}

	public UserRequest(String name, String password, String passwordRepeated, Set<String> roles) {
		super();
		this.name = name;
		this.password = password;
		this.passwordRepeated = passwordRepeated;
		this.roles.addAll(roles);
	}

	public UserRequest(String name, String password, String passwordRepeated, Set<String> roles, Long id) {
		this(name, password, passwordRepeated, roles);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles.addAll(roles);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("UserRequest [name=%s, password=%s, passwordRepeated=%s, roles=%s, id=%s]", name, password,
				passwordRepeated, roles, id);
	}
}
