package com.formulaone.controller.company;

import javax.validation.Valid;

import org.joda.time.DateTime;
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

import com.formulaone.controller.dto.company.CompanyListResponse;
import com.formulaone.controller.dto.company.CompanyRequest;
import com.formulaone.controller.dto.company.CompanyResponse;
import com.formulaone.service.company.CompanyService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@Api(basePath = "/demo", value = "demo", description = "My Company Management APIs")
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@InitBinder
    //protected void initBinder(WebDataBinder binder) {
     //   binder.addValidators(companyValidator);
    //}
	/**
	 * Create new company
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/company", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a company resource.", notes = "Returns the instance of the created company.", response = CompanyResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })
	@PreAuthorize("hasRole('ADMIN')")
	public CompanyResponse createCompany(
			@ApiParam(value = "CompanyRequest object.", required = true) @RequestBody @Valid final CompanyRequest request) {
		try {
			logger.info("Entering the controller with POST method");
			CompanyResponse response = this.companyService.createCompany(request);
			response.setDateTime(new DateTime());
			return response;
		} catch (Exception e) {
			logger.error("Unexpected exception happened", e);
			e.printStackTrace();
			throw e;
		}
	}
	
	
	

	@RequestMapping(value = "/company/namedquery/{name}", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a list of all companies with specific name.", notes = "The list of companies are returned in Wrapper class", response = CompanyListResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
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

	
	
	@RequestMapping(value = "/company/customquery/{name}", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a list of all companies with specific name.", notes = "The list of Companies are returned in Wrapper class", response = CompanyListResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
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

	@RequestMapping(value = "/company/all", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a list of all companies.", notes = "The list of Companies are returned in Wrapper class", response = CompanyListResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })

	@PreAuthorize("hasRole('ADMIN')")
	public CompanyListResponse findAllCompanies() {
		logger.info("Entering the controller with GET method - Custom Query");
		return companyService.findAll();
	}
	
	

	/**
	 * update company information
	 */

	@RequestMapping(value = "/company/update/{id}", method = RequestMethod.PUT, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update company information.", notes = "new inf will be returned in instance of CompanyResponse", response = CompanyResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })
	@PreAuthorize("hasRole('ADMIN')")
	public CompanyResponse updateCompany(
			@ApiParam(value = "The Company id", required = true) @PathVariable(value = "id") String id,
			@RequestBody @Valid final CompanyRequest request) {
		
		logger.info("Entering the controller with PUT method - Updating company info");
		return companyService.update(Integer.valueOf(id), request);
	}

	
	
	/**
	 * Delete company 
	 */

	@RequestMapping(value = "/company/delete/{id}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete info for the specified company Id.", notes = "return no info")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "Success") })

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void deleteCompany(
			@ApiParam(value = "The Company id", required = true) @PathVariable(value = "id") String id) {
		
		logger.info("Entering the controller with DELETE method - Updating company info");
		companyService.delete(Integer.valueOf(id));
	}

}
