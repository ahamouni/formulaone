package com.formulaone.controller.dto.company;

import java.util.ArrayList;
import java.util.List;

public class CompanyListResponse {
	private List<CompanyResponse> companies;

	public CompanyListResponse() {
		super();
		companies = new ArrayList<CompanyResponse>();
	}
	
	
	public void add(CompanyResponse response) {
		this.companies.add(response);
	}
	
	public List<CompanyResponse> getAll() {
		return companies;
	}


	@Override
	public String toString() {
		return "CompanyListResponse [companies=" + companies + "]";
	}

}
