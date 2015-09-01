package com.formulaone.service.company;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formulaone.controller.dto.company.CompanyListResponse;
import com.formulaone.controller.dto.company.CompanyRequest;
import com.formulaone.controller.dto.company.CompanyResponse;
import com.formulaone.domain.company.Company;
import com.formulaone.repository.company.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository repository;

	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see demo.service.CompanyService#createCompany(com.formulaone.api.domain.
	 * CompanyRequest)
	 */

	@Override
	@Transactional
	public CompanyResponse createCompany(CompanyRequest request) {
		Company company = new Company(request.getName(), request.getAge(), request.getAddress(), request.getSalary());
		company = this.repository.save(company);
		return CompanyMapper.map(company);

	}

	@Autowired
	public CompanyServiceImpl(CompanyRepository repository, EntityManager em) {
		super();
		this.repository = repository;
		this.em = em;
	}

	public CompanyListResponse findByCompanyName(String name) {
		List<Company> companies = this.repository.findByName(name);
		return CompanyMapper.map(companies);

	}

	public CompanyListResponse findByCustomQuery(String name) {
		List<Company> companies = this.repository.find(name);
		return CompanyMapper.map(companies);

	}

	public CompanyListResponse findAll() {
		List<Company> companies = this.repository.findAll();
		return CompanyMapper.map(companies);

	}

	@Transactional
	public CompanyResponse update(final Integer id, CompanyRequest request) {
		Company updateInfo = this.repository.findOne(id);
		updateInfo.setAddress(request.getAddress());
		updateInfo.setAge(request.getAge());
		updateInfo.setName(request.getName());
		updateInfo.setSalary(request.getSalary());
		Company updateCompany = this.repository.save(updateInfo);
		return CompanyMapper.map(updateCompany);

	}

	@Transactional
	public void delete(final Integer id) {
		this.repository.delete(id);
	}

}