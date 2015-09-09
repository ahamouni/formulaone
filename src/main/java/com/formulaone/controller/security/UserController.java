package com.formulaone.controller.security;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formulaone.controller.dto.security.UserRequest;
import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.controller.dto.security.validation.UserCreationFormValidator;
import com.formulaone.controller.exceptions.UserNotFoundException;
import com.formulaone.domain.security.UserCredentials;
import com.formulaone.service.security.UserDTOMapper;
import com.formulaone.service.security.UserManagementService;

@RestController
@RequestMapping(value = "/formulaone/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserManagementService userService;
	private final UserCreationFormValidator userCreationValidator;

	@Autowired
	public UserController(UserManagementService userService,
			UserCreationFormValidator userCreationFormValidator) {
		this.userService = userService;
		this.userCreationValidator = userCreationFormValidator;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(userCreationValidator);
	}

	/**
	 * Create new User
	 * 
	 * @param form
	 * @param bindingResult
	 * @return
	 */

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse create(@RequestBody @Valid UserRequest request) {
		logger.debug("Creating a new user entry with information: {}", request);
		return userService.create(request);
	}

	/**
	 * Retrieve user by Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/id/{id:[\\d]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public UserResponse findUserById(@PathVariable(value = "id") Long id) {
		logger.debug("Finding user entry with id: {}" + id);
		UserCredentials user = userService.getUserById(id);
		return UserDTOMapper.map(user);
	}

	/**
	 * Retrieve User by Name
	 * 
	 * @param namme
	 * @return
	 */
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public UserResponse findUserByName(
			@PathVariable(value = "name") String name) {
		logger.debug("Finding user entry with name: {}", name);
		Optional<UserCredentials> user = userService.getUserByName(name);
		if (!user.isPresent()) {
			throw new UserNotFoundException(name);
		}

		logger.debug("Found user entry with information: {}", user);
		// Validation already done in Service layer
		return UserDTOMapper.map(user.get());
	}

	/**
	 * Delete user
	 * 
	 * @param id
	 */
	@RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		logger.debug("Deleting user entry with id: {}", id);
		userService.delete(id);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public UserResponse update(@RequestBody @Valid UserRequest request) {
		logger.debug("Updating a user entry with information: {}", request);
		return userService.update(request);
	}

	/**
	 * Retrieve all Users
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponse> getAllUsers() {
		logger.debug("Finding all users: {}");
		List<UserResponse> users = userService.getAllUsers();
		return users;
	}

}
