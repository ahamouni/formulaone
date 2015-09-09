package com.formulaone.service.merchant;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formulaone.FormulaOneApplication;
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
@IntegrationTest("server.port:0")
public class ReqRespSerialDeserialTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Ignore
	public void testMerchantresponseSerilization() throws Exception {
		MerchantResponse response = new MerchantResponse();
		response.setCompanyName("TamTam ltd.");
		response.setDescription("Company description");
		response.setFirstName("Paul");
		response.setMiddle("William");
		response.setLastName("Jackson");
		response.setTerminalId("131");
		response.setStatus("Approved");
		response.setRequestId(121L);

		String str = objectMapper.writeValueAsString(response);
		System.out.println("Serialized object: " + str);

	}

	@Test
	@Ignore
	public void testMerchantRequestSerialization() throws Exception {

		// Requestjason deserialization

		// CompanyDetails
		CompanyRequest company = new CompanyRequest();
		company.setName("TamTam ltd.");
		company.setPhone("14501234123");

		AddressRequest addr = new AddressRequest();
		addr.setAddress("123 rue Alphonse Daudet");
		addr.setCity("Laval");
		addr.setState("qc");
		addr.setZipCode("H6Y 1R5");
		company.setAddress(addr);

		// build OwnerShip details
		OwnershipDetailsRequest details = new OwnershipDetailsRequest();
		details.setDob(DateTime.now());
		details.setDriverlicense("A123456789876");
		details.setFirstName("Paul");
		details.setLastName("Jackson");
		details.setMiddleName("william");
		details.setPosition("taxi driver");
		details.setSsn("123456");
		details.setTaxiId("taxi12345");

		// bank details
		BankingDetailsRequest bankingDetails = new BankingDetailsRequest();
		bankingDetails.setBankAccountNumber("1234567");
		bankingDetails.setRoutingNumber("12345123");

		// General
		GeneralRequest general = new GeneralRequest();
		general.setAnnualProcessing("annualProcessing");
		general.setCountryOfIncorporation("Canada");
		general.setDescriptor("Descriptor");
		general.setPhoneNumber("1514333444");
		general.setWebsite("http://www.myComany.com");
		general.setBusinessType("online stuff");

		MerchantRequest request = new MerchantRequest("testName",
				"testname@hotmail.com", "legal name llc",
				"describe the business", BigDecimal.valueOf(334.37), company,
				details, bankingDetails, general);

		System.out.println("Request: " + request);
		String str = objectMapper.writeValueAsString(request);
		System.out.println("Serialized obkject: " + str);

	}

	@Test
	@Ignore
	public void testMerchantResponsetDeserialization() throws IOException {
		MerchantResponse response = new MerchantResponse();
		response.setCompanyName("TamTam ltd.");
		response.setDescription("Company description");
		response.setFirstName("Paul");
		response.setMiddle("William");
		response.setLastName("Jackson");
		response.setTerminalId("131");
		response.setStatus("Approved");
		response.setRequestId(121L);

		String str = objectMapper.writeValueAsString(response);

		response = objectMapper.readValue(str, MerchantResponse.class);
		System.out.println("Response is: " + response);

	}

	@Test
	@Ignore
	public void testMerchantRequesttDeserialization() throws Exception {

		// Requestjason deserialization

		// CompanyDetails
		CompanyRequest company = new CompanyRequest();
		company.setName("TamTam ltd.");
		company.setPhone("14501234123");

		AddressRequest addr = new AddressRequest();
		addr.setAddress("123 rue Alphonse Daudet");
		addr.setCity("Laval");
		addr.setState("qc");
		addr.setZipCode("H6Y 1R5");
		company.setAddress(addr);

		// build OwnerShip details
		OwnershipDetailsRequest details = new OwnershipDetailsRequest();
		details.setDob(DateTime.now());
		details.setDriverlicense("A123456789876");
		details.setFirstName("Paul");
		details.setLastName("Jackson");
		details.setMiddleName("william");
		details.setPosition("taxi driver");
		details.setSsn("123456");
		details.setTaxiId("taxi12345");

		// bank details
		BankingDetailsRequest bankingDetails = new BankingDetailsRequest();
		bankingDetails.setBankAccountNumber("1234567");
		bankingDetails.setRoutingNumber("12345123");

		// General
		GeneralRequest general = new GeneralRequest();
		general.setAnnualProcessing("annualProcessing");
		general.setCountryOfIncorporation("Canada");
		general.setDescriptor("Descriptor");
		general.setPhoneNumber("1514333444");
		general.setWebsite("http://www.myComany.com");
		general.setBusinessType("online stuff");

		MerchantRequest request = new MerchantRequest("testName",
				"testname@hotmail.com", "legal name llc",
				"describe the business", BigDecimal.valueOf(334.37), company,
				details, bankingDetails, general);

		System.out.println("Request: " + request);
		String str = objectMapper.writeValueAsString(request);

		MerchantRequest merchantRequest = objectMapper.readValue(str,
				MerchantRequest.class);
		System.out.println("Request is: " + merchantRequest);

	}

}
