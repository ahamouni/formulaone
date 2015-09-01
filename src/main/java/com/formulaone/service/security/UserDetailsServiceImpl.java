package com.formulaone.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formulaone.controller.dto.security.UserCredentialsResponse;
import com.formulaone.domain.security.UserCredentials;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserManagementService userManagementService;

	@Override
	public UserCredentialsResponse loadUserByUsername(String username) throws UsernameNotFoundException {
		UserCredentials user = this.userManagementService.getUserByName(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with name=%s was not found", username)));
		return new UserCredentialsResponse(user);

	}

}
