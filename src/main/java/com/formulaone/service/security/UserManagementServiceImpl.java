package com.formulaone.service.security;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.formulaone.controller.dto.security.UserRequest;
import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.controller.exceptions.UserNotFoundException;
import com.formulaone.domain.security.UserCredentials;
import com.formulaone.repository.security.UserRepository;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserRepository userRepository;

	@Autowired
	public UserManagementServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserResponse create(UserRequest request) {

		logger.debug("Creating a new user entry with information: {}", request);
		UserCredentials user = UserCredentials.getBuilder().name(request.getName())
				.passwordHash(new BCryptPasswordEncoder().encode(request.getPassword())).roles(request.getRoles())
				.build();

		user = userRepository.save(user);

		logger.debug("Created a new user entry with information: {}", user);

		return UserDTOMapper.map(user);
	}

	@Override
	public UserCredentials getUserById(Long id) {
		logger.debug("Finding user entry with id: {}", id);
		UserCredentials user = this.findUserById(id);
		logger.debug("Found user entry with information: {}", user);
		return user;
	}

	@Override
	public Optional<UserCredentials> getUserByName(String name) {
		logger.debug("Getting user by name: {}", name);
		return userRepository.findOneByName(name);

	}

	@Override
	public List<UserResponse> getAllUsers() {
		logger.debug("Getting all users {}");
		List<UserCredentials> users = userRepository.findAll();
		return UserDTOMapper.map(users);
	}

	@Override
	public UserResponse delete(Long id) {
		logger.debug("Deleting user entry with id: {}", id);
		UserCredentials deleted = findUserById(id);
		userRepository.delete(deleted);
		logger.debug("Deleted user entry with information: {}", deleted);
		return UserDTOMapper.map(deleted);
	}

	@Override
	public UserResponse update(UserRequest updateRequest) {
		logger.debug("Updating a user entry with information: {}", updateRequest);
		UserCredentials updated = this.findUserById(updateRequest.getId());
		
		// update with new user info
		updated.update(updateRequest.getName(), new BCryptPasswordEncoder().encode(updateRequest.getPassword()),
				updateRequest.getRoles(), updateRequest.getId());
		updated = userRepository.save(updated);
		
		logger.debug("Updated user entry with information: {}", updated);
		return UserDTOMapper.map(updated);
	}

	private UserCredentials findUserById(Long id) {
		Optional<UserCredentials> result = Optional.ofNullable(userRepository.findOne(id));
		return result.orElseThrow(() -> new UserNotFoundException(id));

	}

}
