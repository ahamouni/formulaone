package com.formulaone.controller.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formulaone.controller.dto.security.UserRequest;
import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.controller.dto.security.validation.UserCreationFormValidator;
import com.formulaone.service.security.UserManagementService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	private static final String NAME = "myName";
	private static final String PASSWORD = "myPassword";
	private static final Set<String> ROLES = new HashSet<>();
	private static final Long ID = 100L;

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private UserManagementService userService;

	@Mock
	private UserCreationFormValidator requestValidator;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, requestValidator)).build();
		ROLES.add("ROLE1");
		ROLES.add("ROLE2");
	}

	@Test
	public void create_OneUser_ShouldCreateNewUserEntry() throws Exception {
		UserRequest userRequest = new UserRequest(NAME, PASSWORD, PASSWORD, ROLES);

		when(requestValidator.supports(UserRequest.class)).thenReturn(true);

		when(userService.create(isA(UserRequest.class))).thenAnswer(new Answer<UserResponse>() {
			@Override
			public UserResponse answer(final InvocationOnMock invocation) throws Throwable {
				UserResponse userResponse = new UserResponse(ID, NAME, ROLES);
				return userResponse;
			}
		});

		mockMvc.perform(post("/formulaone/user")
	    .contentType(APPLICATION_JSON_UTF8)
		.content(new ObjectMapper()
		.writeValueAsBytes(userRequest)))
		.andExpect(status().isCreated())
		.andExpect(content()
		.contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.name", is(NAME)))
		.andExpect(status()
		.isCreated());

		ArgumentCaptor<UserRequest> createdArgument = ArgumentCaptor.forClass(UserRequest.class);
		verify(userService, times(1)).create(createdArgument.capture());
		verifyNoMoreInteractions(userService);

		UserRequest created = createdArgument.getValue();
		assertNotNull(created);
		assertEquals(NAME, created.getName());

	}

}
