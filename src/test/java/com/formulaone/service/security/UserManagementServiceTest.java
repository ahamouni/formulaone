package com.formulaone.service.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.formulaone.controller.dto.security.UserRequest;
import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.controller.exceptions.UserNotFoundException;
import com.formulaone.domain.security.UserCredentials;
import com.formulaone.repository.security.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceTest {

	private static final String NAME = "myName";
	private static final String PASSWORD = "myPassword";
	private static final Set<String> ROLES = new HashSet<>();
	private static final Long ID = 100L;

	@Mock
	private UserRepository repository;

	private UserManagementServiceImpl service;

	@Before
	public void setUp() {
		this.service = new UserManagementServiceImpl(repository);
		ROLES.add("ROLE1");
		ROLES.add("ROLE2");
	}

	@Test
	public void create_ShouldSaveUser() {
		UserRequest newUser = new UserRequest(NAME, PASSWORD, PASSWORD, ROLES);
		when(repository.save(isA(UserCredentials.class))).then(AdditionalAnswers.returnsFirstArg());
		
		UserResponse response = service.create(newUser);
		
		assertNotNull(response);
		assertEquals(newUser.getName(), response.getName());
		
		ArgumentCaptor<UserCredentials> savedUserArgument = ArgumentCaptor.forClass(UserCredentials.class);
		verify(repository, times(1)).save(savedUserArgument.capture());
		verifyNoMoreInteractions(repository);

		UserCredentials userCredentials = savedUserArgument.getValue();
		assertNotNull(userCredentials);
		assertEquals(newUser.getName(), userCredentials.getName());
		assertEquals(newUser.getRoles().size(), userCredentials.getRoles().size());
	}

	@Test
	public void create_ShouldReturnTheInformationOfCreatedUser() {
		UserRequest newUser = new UserRequest(NAME, PASSWORD, PASSWORD, ROLES);

		when(repository.save(isA(UserCredentials.class))).thenAnswer(new Answer<UserCredentials>() {
			@Override
			public UserCredentials answer(final InvocationOnMock invocation) throws Throwable {
				UserCredentials persisted = (UserCredentials) invocation.getArguments()[0];
				ReflectionTestUtils.setField(persisted, "id", ID);
				return persisted;
			}
		});

		UserResponse userResponse = service.create(newUser);
		
		assertNotNull(userResponse);
		assertEquals(newUser.getName(), userResponse.getName());
		assertEquals(ID, userResponse.getId());
		assertEquals(newUser.getRoles().size(), userResponse.getRoles().size());
	}

	@Test
	public void findAll_OneUserFound_shouldReturnInformationOfUser() {
		
		UserCredentials expected = UserCredentials.getBuilder().name(NAME).passwordHash(PASSWORD).roles(ROLES).build();
		when(repository.findAll()).thenReturn(Arrays.asList(expected));
		List<UserResponse> list = service.getAllUsers();
		assertEquals(1, list.size());
		UserResponse actual = list.get(0);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getRoles().size(), actual.getRoles().size());
	}

	@Test(expected = UserNotFoundException.class)
	public void findById_UserNotFound_houldThrowException() {
		when(repository.findOne(ID)).thenReturn(null);
		this.service.getUserById(ID);
		verify(repository, times(1)).findOne(ID);
	}

	@Test
	public void delete_UserFound_ShouldDeleteTheUserEntry() {
		UserCredentials deleted = UserCredentials.getBuilder().name(NAME).passwordHash(PASSWORD).roles(ROLES).build();
		when(repository.findOne(ID)).thenReturn(deleted);
		service.delete(ID);
		verify(repository, times(1)).delete(deleted);
	}

	@Test(expected = UserNotFoundException.class)
	public void delete_UserNotFound_ShouldThrowException() {
		UserCredentials deleted = UserCredentials.getBuilder().name(NAME).passwordHash(PASSWORD).roles(ROLES).build();
		when(repository.findOne(ID)).thenReturn(null);
		service.delete(ID);
		verify(repository, times(1)).delete(deleted);
	}

	@Test(expected = UserNotFoundException.class)
	public void Update_UserNotFound_ShouldThrowException() {
		when(repository.findOne(ID)).thenReturn(null);

		UserRequest updated = new UserRequest(NAME, PASSWORD, PASSWORD, ROLES);
		service.update(updated);

		verify(repository, times(1)).findOne(ID);

	}

	@Test
	@Ignore
	public void Update_UserFound_ShouldUpdateuserInformation() {

		UserCredentials actual = UserCredentials.getBuilder().build();
		ReflectionTestUtils.setField(actual, "id", ID);

		UserRequest request = new UserRequest(NAME, PASSWORD, PASSWORD, ROLES);
		request.setId(ID);

		when(repository.findOne(ID)).thenReturn(actual);
		when(repository.save(actual)).thenReturn(actual);

		service.update(request);

		assertEquals(actual.getName(), NAME);
		assertEquals(ROLES.size(), actual.getRoles().size());

		verify(repository, times(1)).findOne(ID);
		verify(repository, times(1)).save(actual);

	}

}
