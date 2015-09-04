package com.formulaone.controller.merchant;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.controller.dto.merchant.validation.MerchantValidator;
import com.formulaone.service.merchant.MerchantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json; charset=UTF-8")
@RequestMapping(value = "/formulaone/merchant", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantController {

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private MerchantValidator merchantValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(merchantValidator);
	}

	/**
	 * Just to test that the deployment, specially to heroku, is done correctly.
	 * TO BE Removed!
	 */

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('USER')")
	public List<MerchantResponse> findCompany() {
		logger.info(
				"Entering the controller with GET method - retrieving all merchants");
		List<MerchantResponse> findall = merchantService.findall();
		if (findall != null && !findall.isEmpty()) {
			findall = Arrays.asList(findall.get(0));
		}

		return findall;
	}

	/**
	 * Create new Merchant
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = {
			"application/json" }, produces = {
					"application/json; charset=UTF-8" })
	@ApiOperation(httpMethod = "POST", value = "Boarding new merchant", notes = "The merchant is Id verified before processing the payment.", response = MerchantResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful merchant boarding", response = MerchantResponse.class),
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public MerchantResponse createMerchant(
			@ApiParam(name = "merchantRequest", value = "Merchant information", required = true) @RequestBody @Valid final MerchantRequest request) {
		try {
			logger.debug(
					"Entering the Merchant controller with POST(create) method");
			MerchantResponse response = merchantService.createMerchant(request);
			return response;
		} catch (Exception e) {
			logger.error("Unexpected exception happened", e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Retrieve all registered merchants
	 */

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Returns all boarded merchants", notes = "Returns a list of all boarded merchants", response = MerchantResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful merchant retrieval", response = MerchantResponse.class),
			@ApiResponse(code = 500, message = "Internal server error") })

	@PreAuthorize("hasRole('USER')")
	public List<MerchantResponse> findAllCompanies() {
		logger.info(
				"Entering the controller with GET method - retrieving all merchants");
		return merchantService.findall();
	}

}
