package com.formulaone.controller.merchant;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
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
import org.springframework.core.ParameterizedTypeReference;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formulaone.FormulaOneApplication;
import com.formulaone.config.Constants;
import com.formulaone.controller.dto.merchant.AddressRequest;
import com.formulaone.controller.dto.merchant.BankingDetailsRequest;
import com.formulaone.controller.dto.merchant.CompanyRequest;
import com.formulaone.controller.dto.merchant.GeneralRequest;
import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.controller.dto.merchant.OwnershipDetailsRequest;

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

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private HttpHeaders httpHeaders;

	private static MerchantRequest request;
	private static Long createdId;

	// Heroku url "http://b2bformulaone.herokuapp.com/formulaone/merchant"
	//

	@Before
	public void setUp() throws Exception {

		this.base = new URL(
				"http://localhost:" + port + "/formulaone/merchant");
		// "http://b2bformulaone.herokuapp.com/formulaone/merchant");
		System.out.println("LE PORT: " + port);
		httpHeaders = createHeaders();
	}

	/**
	 * Test successful merchant creation
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_1_SuccessfulMerchantCreation() throws Exception {

		request = createMerchant();
		HttpEntity<MerchantRequest> entity = new HttpEntity<MerchantRequest>(
				request, httpHeaders);
		try {
			ResponseEntity<MerchantResponse> response = restTemplate.exchange(
					base.toString(), HttpMethod.POST, entity,
					MerchantResponse.class);

			MerchantResponse merchantResponse = response.getBody();
			assertThat(merchantResponse, notNullValue());
			assertThat(merchantResponse.getCompanyName(),
					equalTo(request.getCompany().getName()));
			assertThat(merchantResponse.getDescription(),
					equalTo(request.getBusinessDescription()));
			assertThat(merchantResponse.getFirstName(),
					equalTo(request.getOwnershipDetails().getFirstName()));
			assertThat(merchantResponse.getLastName(),
					equalTo(request.getOwnershipDetails().getLastName()));
			assertThat(merchantResponse.getMid(), notNullValue());

			assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

			createdId = merchantResponse.getId();

		} catch (Exception e) {
			fail("Unexpected exception happened: " + e.getMessage());

		}
	}

	/**
	 * Test Merchant retrieval by Id
	 */
	@Test
	public void test_2_SuccessfulMerchantRetrievalById() {
		Map<String, Long> vars = new HashMap<String, Long>();
		vars.put("id", createdId);
		try {
			ResponseEntity<MerchantResponse> response = restTemplate.exchange(
					base + "/{id}", HttpMethod.GET,
					new HttpEntity<MerchantResponse>(httpHeaders),
					MerchantResponse.class, vars);

			MerchantResponse merchantResponse = response.getBody();
			assertThat(merchantResponse, notNullValue());
			assertThat(merchantResponse.getCompanyName(),
					equalTo(request.getCompany().getName()));
			assertThat(merchantResponse.getDescription(),
					equalTo(request.getBusinessDescription()));
			assertThat(merchantResponse.getFirstName(),
					equalTo(request.getOwnershipDetails().getFirstName()));
			assertThat(merchantResponse.getLastName(),
					equalTo(request.getOwnershipDetails().getLastName()));
			assertThat(merchantResponse.getMid(), notNullValue());
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		} catch (RestClientException e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}
	}

	/**
	 * test successful all mnerchants retrieval
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void test_3_SuccessfulAllMerchantsRetrieval()
			throws JsonParseException, JsonMappingException, IOException {
		try {
			@SuppressWarnings("unchecked")
			ResponseEntity<List<MerchantResponse>> response = restTemplate
					.exchange(base + "", HttpMethod.GET,
							new HttpEntity<List<MerchantResponse>>(httpHeaders),
							new ParameterizedTypeReference<List<MerchantResponse>>() {
							});

			assertThat(response, notNullValue());
			assertThat(response.getBody(), notNullValue());
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

			List<MerchantResponse> merchantResponses = response.getBody();
			assertThat(merchantResponses, notNullValue());
			assertThat(merchantResponses.size(), equalTo(Integer.valueOf(1)));

			for (MerchantResponse merchantResponse : merchantResponses) {
				assertThat(merchantResponse.getCompanyName(),
						equalTo(request.getCompany().getName()));
				assertThat(merchantResponse.getDescription(),
						equalTo(request.getBusinessDescription()));
				assertThat(merchantResponse.getFirstName(),
						equalTo(request.getOwnershipDetails().getFirstName()));
				assertThat(merchantResponse.getLastName(),
						equalTo(request.getOwnershipDetails().getLastName()));
				assertThat(merchantResponse.getMid(), notNullValue());

			}

		} catch (RestClientException e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}
	}

	/**
	 * Test successful user deletion
	 */
	@Test
	public void test_4_SuccessMerchantDeletion() {
		Map<String, Long> vars = new HashMap<String, Long>();
		vars.put("id", createdId);

		try {
			ResponseEntity<String> resp = restTemplate.exchange(base + "/{id}",
					HttpMethod.DELETE, new HttpEntity<String>(httpHeaders),
					String.class, vars);

			assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (RestClientException e) {
			fail("Unexpected exception happened: " + e.getMessage());
		}
	}

	private HttpHeaders createHeaders() {
		return new HttpHeaders() {
			private static final long serialVersionUID = 1L;

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

	private MerchantRequest createMerchant() throws Exception {
		// CompanyDetails
		CompanyRequest company = new CompanyRequest();
		company.setName("TamTam ltd");
		company.setPhone("14501234123");

		AddressRequest addr = new AddressRequest();
		addr.setAddress("123 rue Alphonse Daudet");
		addr.setCity("Laval");
		addr.setState("NE");
		addr.setZipCode("H6Y 1R5");
		addr.setCountry("US");
		company.setAddress(addr);

		// build OwnerShip details
		OwnershipDetailsRequest details = new OwnershipDetailsRequest();
		details.setDob(DateTime.now());
		details.setDriverlicense("A123456789876");
		details.setFirstName("Paul");
		details.setLastName("Jackson");
		details.setMiddleName("william");
		details.setPosition("taxi driver");
		details.setSsn("221-09-9307");
		details.setTaxiId("taxi123458");

		// bank details
		BankingDetailsRequest bankingDetails = new BankingDetailsRequest();
		bankingDetails.setBankAccountNumber("1234567");
		bankingDetails.setRoutingNumber("12345123");

		// General
		GeneralRequest general = new GeneralRequest();
		general.setAnnualProcessing("annualProcessing");
		general.setCountryOfIncorporation("CA");
		general.setDescriptor("Descriptor 1234567891234567891234567");
		general.setPhoneNumber("1514333444");
		general.setWebsite("http://www.myComany.com");
		general.setBusinessType("online stuff");

		MerchantRequest request = new MerchantRequest("testName",
				"testname@hotmail.com", "legal name llc",
				"describe the business", BigDecimal.valueOf(13576.23), company,
				details, bankingDetails, general);

		request.setMid(1000l);

		return request;
	}

}
