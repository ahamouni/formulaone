package com.formulaone.service.security;

import java.util.List;
import java.util.Optional;

import com.formulaone.controller.dto.security.UserRequest;
import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.controller.exceptions.UserNotFoundException;
import com.formulaone.domain.security.UserCredentials;

public interface UserManagementService {

	/**
	 * Creates a new user entry.
	 * 
	 * @param trequest The information of the created user entry.
	 * @return The information of the created user entry.
	 */
	UserResponse create(UserRequest request);

	/**
	 * Retrieve user by its ID
	 * 
	 * @param id  user Id
	 * @return The information of the user entry
	 * @throws UserNotFoundException  if user entry does not exists
	 */
	UserCredentials getUserById(Long id);

	/**
	 * Delete user entry
	 * 
	 * @param id  user Id
	 * @return The information of the deleted user
	 * @throws UserNotFoundException  if user entry does not exists
	 */

	/**
	 * Retrieve user information by its name
	 * 
	 * @param name  user name
	 * @return The information of the user entry
	 * @throws UserNotFoundException  if user entry does not exists
	 */
	Optional<UserCredentials> getUserByName(String name);

	/**
	 * Find all users entries
	 * 
	 * @return List of users information
	 */
	List<UserResponse> getAllUsers();

	UserResponse delete(Long id);

	/**
	 * Update the information of the user entry
	 * 
	 * @param update  The information of the updated entry
	 * @return The information of the updated entry
	 * @throws UserNotFoundException if user entry does not exists
	 */
	UserResponse update(UserRequest update);

}
