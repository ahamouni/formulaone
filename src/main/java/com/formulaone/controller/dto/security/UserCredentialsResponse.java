package com.formulaone.controller.dto.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.formulaone.domain.security.UserCredentials;

public class UserCredentialsResponse extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 8996378937988755296L;

	private UserCredentials user;

	public UserCredentialsResponse(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}

	public UserCredentialsResponse(UserCredentials user) {
		super(user.getName(), user.getPasswordHash(),
				AuthorityUtils.createAuthorityList(user.getRolesNames().toArray(new String[user.getRoles().size()])));
		this.user = user;

	}

	@Override
	public String toString() {
		return "CurrentUser{" + "user=" + user + "} " + super.toString();
	}

}
