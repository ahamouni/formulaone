package com.formulaone.controller.company;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formulaone.controller.dto.company.CompanyListResponse;
import com.formulaone.controller.dto.company.CompanyRequest;
import com.formulaone.controller.dto.company.CompanyResponse;
import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.service.company.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json; charset=UTF-8")
@RequestMapping(value = "/formulaone/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	// protected void initBinder(WebDataBinder binder) {
	// binder.addValidators(companyValidator);
	// }
	/**
	 * Create new company
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a company resource.", notes = "Returns the instance of the created company.", response = CompanyResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful merchant retrieval", response = MerchantResponse.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasRole('ADMIN')")
	public CompanyResponse createCompany(
			@ApiParam(value = "CompanyRequest object.", required = true) @RequestBody @Valid final CompanyRequest request) {
		try {
			logger.info("Entering the controller with POST method");
			CompanyResponse response = this.companyService
					.createCompany(request);
			response.setDateTime(new DateTime());
			return response;
		} catch (Exception e) {
			logger.error("Unexpected exception happened", e);
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/namedquery/{name}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a list of all companies with specific name.", notes = "The list of companies are returned in Wrapper class", response = CompanyListResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "") })
	@PreAuthorize("hasRole('ADMIN')")
	public CompanyListResponse findCompanies(
			@ApiParam(value = "The Company name", required = true) @PathVariable(value = "name") String name) {
		// final String s = null;
		// if(s == null) {
		// throw new DataFormatException("Hi man there was an error!!");
		// }

		logger.info("Entering the controller with GET method - Named Query");
		return companyService.findByCompanyName(name);
	}

	@RequestMapping(value = "/customquery/{name}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a list of all companies with specific name.", notes = "The list of Companies are returned in Wrapper class", response = CompanyListResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "") })

	@PreAuthorize("hasRole('ADMIN')")
	public CompanyListResponse findCompaniesByCustomQuery(
			@ApiParam(value = "The Company name", required = true) @PathVariable(value = "name") String name) {
		logger.info("Entering the controller with GET method - Custom Query");
		return companyService.findByCustomQuery(name);
	}

	/**
	 * Retrieve all companies
	 */

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a list of all companies.", notes = "The list of Companies are returned in Wrapper class", response = CompanyListResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })

	@PreAuthorize("hasRole('ADMIN')")
	public CompanyListResponse findAllCompanies() {
		logger.info("Entering the controller with GET method - Custom Query");
		return companyService.findAll();
	}

	/**
	 * update company information
	 */

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update company information.", notes = "new inf will be returned in instance of CompanyResponse", response = CompanyResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })
	@PreAuthorize("hasRole('ADMIN')")
	public CompanyResponse updateCompany(
			@ApiParam(value = "The Company id", required = true) @PathVariable(value = "id") String id,
			@RequestBody @Valid final CompanyRequest request) {

		logger.info(
				"Entering the controller with PUT method - Updating company info");
		return companyService.update(Integer.valueOf(id), request);
	}

	/**
	 * Delete company
	 */

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete info for the specified company Id.", notes = "return no info")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void deleteCompany(
			@ApiParam(value = "The Company id", required = true) @PathVariable(value = "id") String id) {

		logger.info(
				"Entering the controller with DELETE method - Updating company info");
		companyService.delete(Integer.valueOf(id));
	}

}
