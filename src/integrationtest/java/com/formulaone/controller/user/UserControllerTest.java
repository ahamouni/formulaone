package com.formulaone.controller.user;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientException;

import com.formulaone.FormulaOneApplication;
import com.formulaone.controller.dto.security.UserRequest;
import com.formulaone.controller.dto.security.UserResponse;
import com.formulaone.domain.security.RoleEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FormulaOneApplication.class)
@ActiveProfiles("qa")
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@IntegrationTest("server.port:0")

/**
 * Integration tests for UserController rest service Note: for testing purpose,
 * The user 'admin' with password 'secret' is always loaded through liquibase
 * when starting server.
 * 
 */
public class UserControllerTest {

	private static final String ADMIN_USER = "admin";
	private static final String ADMIN_PWD = "secret";

	private static final String NEW_USER = "user_1";
	private static final String NEW_PASSWORD = "secret";
	private static final String CREATED_USER = NEW_USER;

	private static final String UPDATED_USER = "updt_user";
	private static final String UPDATED_PASSWORD = "updt_secret";

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private HttpHeaders httpHeaders;
	private static Long createdId;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/formulaone/user");
		httpHeaders = createHeaders();
	}

	/**
	 * Test User creation
	 */
	@Test
	public void test_1_SuccessfulUserCredentialCreation() {

		Set<String> roles = new HashSet<>();
		roles.add(RoleEnum.ADMIN.name());
		roles.add(RoleEnum.USER.name());
		UserRequest request = new UserRequest(NEW_USER, NEW_PASSWORD, NEW_PASSWORD, roles);
		HttpEntity<UserRequest> entity = new HttpEntity<UserRequest>(request, httpHeaders);

		try {
			ResponseEntity<UserResponse> response = restTemplate.exchange(base.toString(), HttpMethod.POST, entity,
					UserResponse.class);

			UserResponse userResponse = response.getBody();
			assertThat(userResponse, notNullValue());
			assertThat(userResponse.getName(), equalTo(CREATED_USER));
			assertThat(userResponse.getRoles().size(), equalTo(2));
			assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

			createdId = userResponse.getId();
		} catch (Exception e) {
			fail("Unexpected exception happened: " + e.getMessage());

		}
	}

	/**
	 * Test user retrieval by Id
	 */
	@Test
	public void test_2_SuccessfulUserRetrievalById() {
		Map<String, Long> vars = new HashMap<String, Long>();
		System.out.println("ID IS " + createdId);
		vars.put("id", createdId);
		try {
			ResponseEntity<UserResponse> response = restTemplate.exchange(base + "/id/{id}", HttpMethod.GET,
					new HttpEntity<UserResponse>(httpHeaders), UserResponse.class, vars);

			UserResponse userResponse = response.getBody();
			assertThat(userResponse, notNullValue());
			assertThat(userResponse.getName(), equalTo(CREATED_USER));
			assertThat(userResponse.getRoles().size(), equalTo(2));
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		} catch (RestClientException e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}
	}

	/**
	 * Test all users retrieval
	 */
	@Test
	public void test_3_SuccessfulAllUsersRetrieval() {
		try {
			ResponseEntity<ArrayList> response = restTemplate.exchange(base + "", HttpMethod.GET,
					new HttpEntity<List<UserResponse>>(httpHeaders), ArrayList.class);

			assertThat(response, notNullValue());
			assertThat(response.getBody(), notNullValue());
			assertThat(response.getBody().size(), equalTo(2));

		} catch (RestClientException e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}
	}

	/**
	 * Test User retrieval by name
	 */
	@Test
	public void test_4_SuccessfulUserRetrievalByName() {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", CREATED_USER);
		try {
			ResponseEntity<UserResponse> response = restTemplate.exchange(base + "/name/{name}", HttpMethod.GET,
					new HttpEntity<UserResponse>(httpHeaders), UserResponse.class, vars);

			UserResponse userResponse = response.getBody();
			assertThat(userResponse, notNullValue());
			assertThat(userResponse.getName(), equalTo(CREATED_USER));
			assertThat(userResponse.getRoles().size(), equalTo(2));
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		} catch (RestClientException e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}
	}

	/**
	 * Test successsful user update
	 */
	@Test
	public void test_5_UpdateUser() {
		Map<String, Long> vars = new HashMap<String, Long>();
		vars.put("id", createdId);
		ResponseEntity<UserResponse> response = restTemplate.exchange(base + "/id/{id}", HttpMethod.GET,
				new HttpEntity<UserResponse>(httpHeaders), UserResponse.class, vars);

		UserResponse userResponse = response.getBody();
		assertThat(userResponse, notNullValue());

		Set<String> roles = new HashSet<>();
		roles.add(RoleEnum.USER.name());
		UserRequest request = new UserRequest(UPDATED_USER, UPDATED_PASSWORD, UPDATED_PASSWORD, roles, createdId);

		HttpEntity<UserRequest> entity = new HttpEntity<UserRequest>(request, httpHeaders);

		try {
			ResponseEntity<UserResponse> resp = restTemplate.exchange(base.toString(), HttpMethod.PUT, entity,
					UserResponse.class);

			userResponse = resp.getBody();
			assertThat(userResponse, notNullValue());
			assertThat(userResponse.getName(), equalTo(UPDATED_USER));
			assertThat(userResponse.getRoles().size(), equalTo(1));
			assertThat(userResponse.getRoles().iterator().next(), equalTo("USER"));
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		} catch (Exception e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}

	}

	/**
	 * Test successful user deletion
	 */
	@Test
	public void test_6_DeleteUser() {
		Map<String, Long> vars = new HashMap<String, Long>();
		// vars.put("id", user.getId());
		vars.put("id", createdId);

		try {
			ResponseEntity<UserResponse> resp = restTemplate.exchange(base + "/{id}", HttpMethod.DELETE,
					new HttpEntity<UserResponse>(httpHeaders), UserResponse.class, vars);

			UserResponse userResponse = resp.getBody();
			assertThat(userResponse, nullValue());
		} catch (RestClientException e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}
	}

	private HttpHeaders createHeaders() {
		return new HttpHeaders() {
			{
				setContentType(MediaType.APPLICATION_JSON);
				setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

				String auth = ADMIN_USER + ":" + ADMIN_PWD;
				byte[] encodedAuth = Base64.encode(auth.getBytes());
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);

			}
		};
	}

}
