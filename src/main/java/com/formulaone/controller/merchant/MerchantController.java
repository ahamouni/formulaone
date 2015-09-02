package com.formulaone.controller.merchant;

import java.util.ArrayList;
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
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@Api(basePath = "/formulaone/merchant", value = "formulaone", description = "Merchant Boarding APIs")
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
	 * Create new Merchant
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = {
			"application/json" }, produces = {
					"application/json; charset=UTF-8" })
	@ApiOperation(value = "Create new Merchant", notes = "", response = MerchantResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public MerchantResponse createMerchant(
			@ApiParam(value = "MerchantRequest instance", required = true) @RequestBody @Valid final MerchantRequest request) {
		try {
			logger.debug("Entering the Merchant controller with POST(create) method");
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
	@ApiOperation(value = "Return all boarded merchants", notes = "Values are returned in List",
	          response = ArrayList.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })

	@PreAuthorize("hasRole('ADMIN')")
	public List<MerchantResponse> findAllCompanies() {
		logger.info("Entering the controller with GET method - retrieving all merchants");
		return merchantService.findall();
	}


}
