package com.formulaone.controller.merchant;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
import com.formulaone.config.Constants;
import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.domain.security.RoleEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FormulaOneApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@IntegrationTest("server.port:0")
@ActiveProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)

/**
 * Integration tests for MerchantController rest service Note: for testing
 * purpose, The user 'admin' with password 'secret' is always loaded through
 * liquibase when starting server.
 * 
 */
public class MerchantControllerTest {
	private static final String ADMIN_USER = "admin";
	private static final String ADMIN_PWD = "secret";
	
	//TODO Complete all tests!!

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private HttpHeaders httpHeaders;

	@Before
	public void setUp() throws Exception {
		
		//Heroku url
		this.base = new URL(
				"http://b2bformulaone.herokuapp.com/formulaone/merchant");
	   
		//this.base = new URL("http://localhost:" + port + "/formulaone/merchant");
	
		
		System.out.println("LE PORT: " + port);
		httpHeaders = createHeaders();
	}

	/**
	 * Test successful merchant creation
	 */
	@Test
	public void test_1_SuccessfulMerchantCreation() {

		Set<String> roles = new HashSet<>();
		roles.add(RoleEnum.USER.name());
		MerchantRequest request = new MerchantRequest();
		request.setName("MyCieIIII");
		request.setLegalName("MyCie II LLc.");
		HttpEntity<MerchantRequest> entity = new HttpEntity<MerchantRequest>(
				request, httpHeaders);
		try {
			ResponseEntity<MerchantResponse> response = restTemplate.exchange(
					base.toString(), HttpMethod.POST, entity,
					MerchantResponse.class);

			MerchantResponse userResponse = response.getBody();
			assertThat(userResponse, notNullValue());
			assertThat(userResponse.getCompanyName(),
					equalTo(request.getName()));
			assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		} catch (Exception e) {
			fail("Unexpected exception happened: " + e.getMessage());

		}
	}

	/**
	 * test successful all mnerchants retrieval
	 */
	@Test
	public void test_3_SuccessfulAllMerchantsRetrieval() {
		try {
			ResponseEntity<ArrayList> response = restTemplate.exchange(
					base + "", HttpMethod.GET,
					new HttpEntity<List<MerchantResponse>>(httpHeaders),
					ArrayList.class);

			assertThat(response, notNullValue());
			assertThat(response.getBody(), notNullValue());
			Map<String,String> map = (Map<String, String>) response.getBody().get(0);
			System.out.println("Merchant: " + map.get("companyName"));

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
