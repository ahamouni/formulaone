package com.formulaone.service.security;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.formulaone.FormulaOneApplication;
import com.formulaone.controller.dto.company.CompanyRequest;
import com.formulaone.controller.dto.company.CompanyResponse;
import com.formulaone.domain.company.Company;
import com.formulaone.repository.company.CompanyRepository;
import com.formulaone.service.company.CompanyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FormulaOneApplication.class)
@WebAppConfiguration
public class DemoApplicationSpyTest {
	
		@InjectMocks
		@Spy
		private CompanyServiceImpl companyService;

		@Mock
		private EntityManager em;
		
		@Mock
		private CompanyRepository repository;

		@Before
		public void init() {
			MockitoAnnotations.initMocks(this);
	}

		@Test
		public void testCreatePerson() {

			Company company = new Company("aa", 23, "no address", 67.09F);
			Mockito.when(repository.save(org.mockito.Matchers.any(Company.class))).thenReturn(company);
			
		    CompanyResponse response = companyService.createCompany(
				new CompanyRequest(company.getName(), company.getAge(), company.getAddress(), company.getSalary(), new DateTime()));
		
			assertThat(response, notNullValue(CompanyResponse.class));
			assertThat(response.getName(), equalTo(company.getName()));
			assertThat(response.getAge(), equalTo(company.getAge()));
			assertThat(response.getAddress(), equalTo(company.getAddress()));
			assertThat(response.getSalary(), equalTo(company.getSalary()));
			
			Mockito.verify(repository, Mockito.times(1)).save(org.mockito.Matchers.any(Company.class));



		}


}
