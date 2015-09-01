package com.formulaone.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@NamedQueries({
@NamedQuery(name = "Company.findByTheCompanyName", 	query = "from Company c where c.name = ?1" )
})
@Entity
public class Company {
	
	@Id
    @SequenceGenerator(name="company_idcompany_sequence",
                       sequenceName="company_idcompany_sequence",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="company_idcompany_sequence")
    @Column(name = "id", updatable=false)
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "AGE")
	private Integer age;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "SALARY")
	private Float salary;
	
	

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	
		

	public Company(String name, Integer age, String address, Float salary) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
		this.salary = salary;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}


	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", age=" + age + ", address=" + address + ", salary=" + salary
				+ "]";
	} 
}
