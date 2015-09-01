package com.formulaone.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.formulaone.domain.BaseAuditingEntity;

@Entity
@Table(name = "merchant")
public class Merchant extends BaseAuditingEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "name", length = 50, nullable = false, unique = false)
	@Size(max = 50)
	private String name;

	
	public Merchant() {
		super();
	}


	public Merchant(String name) {
		super();
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

}
