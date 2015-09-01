package com.formulaone.controller.dto.company;

import java.io.Serializable;

import org.joda.time.DateTime;

public class CompanyResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer age;
	private String address;
	private Float salary;
	private Integer id;
	
	//Added for testing purpose
	private DateTime dateTime;

	public CompanyResponse() {
		super();
	}

	public CompanyResponse(Integer id, String name, Integer age, String address, Float salary) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public Float getSalary() {
		return salary;
	}

	public Integer getId() {
		return id;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "CompanyResponse [name=" + name + ", age=" + age + ", address=" + address + ", salary=" + salary
				+ ", id=" + id + ", dateTime=" + dateTime + "]";
	}
}
