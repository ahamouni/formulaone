package com.formulaone.service.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.domain.security.Role;
import com.formulaone.domain.security.UserCredentials;

/**
 * Maps UserCredentials entity to UserResponse DTO
 * 
 * @author
 *
 */
public class UserDTOMapper {

	public static List<UserResponse> map(List<UserCredentials> users) {
		List<UserResponse> userResponses = new ArrayList<>();
		for (UserCredentials user : users) {
			userResponses.add(map(user));
		}
		return userResponses;
	}

	public static UserResponse map(UserCredentials user) {
		Set<Role> roles = user.getRoles();
		Set<String> rolesName = new HashSet<>();
		for (Role role : roles) {
			rolesName.add(role.getName());
		}
		return new UserResponse(user.getId(), user.getName(), getRolesNames(user.getRoles()));
	}

	/**
	 * Retrieve For each role entity its role name.
	 * 
	 * @param roles
	 * @return
	 */
	private static Set<String> getRolesNames(Set<Role> roles) {
		Set<String> rolesNames = new HashSet<>();
		for (Role role : roles) {
			rolesNames.add(role.getName());
		}
		return rolesNames;
	}

}
