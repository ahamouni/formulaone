package com.formulaone.controller.dto.company;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.formulaone.jason.JasonDateSerializer;

public class CompanyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Length(min=1, max=10, message = "error.name.size")
	@NotNull(message = "error.name.notnull")
	private String name;
	
	@Range(min=1, max=100, message="error.age.size")
	@NotNull(message = "error.age.notnull")
	private Integer age;
	
	@Length(min=1, max=40, message="error.address.size")
	@NotNull(message = "error.address.notnull")
	private String address;
	
	private Float salary;
	
	private DateTime dateTime;

	
	public CompanyRequest() {
		super();
	}

	public CompanyRequest(String name, Integer age, String address, Float salary, DateTime time) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
		this.salary = salary;
		this.dateTime = time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	@JsonSerialize(using = JasonDateSerializer.class)
	public DateTime getDateTime() {
		return dateTime;
	}

	@Override
	public String toString() {
		return "CompanyRequest [name=" + name + ", age=" + age + ", address=" + address + ", salary=" + salary
				+ ", dateTime=" + dateTime + "]";
	}

	
}
