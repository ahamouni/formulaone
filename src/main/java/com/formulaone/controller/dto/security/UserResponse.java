package com.formulaone.controller.dto.security;

import java.util.HashSet;
import java.util.Set;

public class UserResponse {

	private Long id;
	private String name;
	private Set<String> roles = new HashSet<>();

	public UserResponse() {
		super();
	}

	public UserResponse(Long id, String name, Set<String> roles) {
		super();
		this.id = id;
		this.name = name;
		this.roles.addAll(roles);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", name=" + name + ", roles=" + roles + "]";
	}

}
