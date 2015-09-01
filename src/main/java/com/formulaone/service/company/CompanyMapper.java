package com.formulaone.service.company;

import java.util.List;

import com.formulaone.controller.dto.company.CompanyListResponse;
import com.formulaone.controller.dto.company.CompanyResponse;
import com.formulaone.domain.company.Company;

public class CompanyMapper {

	public static CompanyResponse map(Company company) {
		return new CompanyResponse(company.getId(), company.getName(), company.getAge(), company.getAddress(),
				company.getSalary());
	}

	public static CompanyListResponse map(List<Company> companies) {

		CompanyListResponse companyListResponse = new CompanyListResponse();
		for (Company company : companies) {
			companyListResponse.add(new CompanyResponse(company.getId(), company.getName(), company.getAge(),
					company.getAddress(), company.getSalary()));
		}

		return companyListResponse;
	}

}
