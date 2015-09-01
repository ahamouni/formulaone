package com.formulaone.service.company;

import com.formulaone.controller.dto.company.CompanyListResponse;
import com.formulaone.controller.dto.company.CompanyRequest;
import com.formulaone.controller.dto.company.CompanyResponse;

public interface CompanyService {

	public CompanyResponse createCompany(CompanyRequest request);
	public CompanyResponse update(Integer id, CompanyRequest request);
	public void delete(final Integer id);
	public CompanyListResponse findByCompanyName(String name);
	public CompanyListResponse findByCustomQuery(String name);
	public CompanyListResponse findAll();


}