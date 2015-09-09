package com.formulaone.controller.merchant;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.controller.dto.security.validation.MerchantCreationFormValidator;
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

	private MerchantService merchantService;
	private MerchantCreationFormValidator merchantCreationFormValidator;

	@Autowired
	public MerchantController(MerchantService merchantService,
			MerchantCreationFormValidator merchantCreationFormValidator) {
		super();
		this.merchantService = merchantService;
		this.merchantCreationFormValidator = merchantCreationFormValidator;
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(merchantCreationFormValidator);
	}

	/**
	 * Create new Merchant
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "Boarding new merchant", notes = "", response = MerchantResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successful merchant boarding", response = MerchantResponse.class),
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public MerchantResponse createMerchant(
			@ApiParam(name = "merchantRequest", value = "Merchant information", required = true) @RequestBody @Valid final MerchantRequest request) {

		logger.debug(
				"Entering the Merchant controller with POST(create) method");
		MerchantResponse response = merchantService.createMerchant(request);
		return response;
	}

	/**
	 * Retrieve all registered merchants
	 */

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Retrieve all boarded merchants", notes = "Merchants are returned in the LIST", response = MerchantResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful merchant retrieval", response = MerchantResponse.class),
			@ApiResponse(code = 500, message = "Internal server error") })

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	public List<MerchantResponse> findAllMerchants() {
		logger.info(
				"Entering the controller with GET method - retrieving all merchants");
		return merchantService.findall();
	}

	/**
	 * Retrieve Merchant with the specified merchant Id
	 */
	@RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Retrieve merchant", notes = "Returns merchant details by Identifier", response = MerchantResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful merchant retrieval", response = MerchantResponse.class),
			@ApiResponse(code = 404, message = "Merchant not found"),
			@ApiResponse(code = 500, message = "Internal server error") })

	@PreAuthorize("hasRole('USER')")
	public MerchantResponse retrieveMerchant(
			@ApiParam(value = "Merchant id", required = true) @PathVariable(value = "id") Long id) {
		logger.info("Entering the Merchant controller with GET method by Id");
		return merchantService.findMerchantById(id);
	}

	/**
	 * Delete Merchant with the specified merchant Id
	 */
	@RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "DELETE", value = "Delete merchant", notes = "Delete merchant details by Identifier")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful merchant deletion", response = MerchantResponse.class),
			@ApiResponse(code = 404, message = "Merchant not found"),
			@ApiResponse(code = 500, message = "Internal server error") })

	@PreAuthorize("hasRole('USER')")
	public void deleteMerchant(
			@ApiParam(value = "Merchant id", required = true) @PathVariable(value = "id") Long id) {
		logger.info(
				"Entering the Merchant controller with DELETE by Id method");
		merchantService.delete(id);
	}

}
