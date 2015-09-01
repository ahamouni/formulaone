package com.formulaone.repository.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.formulaone.domain.company.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	List<Company> findByName(String name);
	
	@Query("SELECT c FROM Company c WHERE LOWER(c.name) = LOWER(:name)")
    public List<Company> find(@Param("name") String name);
	

}
