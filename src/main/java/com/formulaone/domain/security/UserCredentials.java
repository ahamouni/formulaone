package com.formulaone.domain.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.formulaone.domain.BaseAuditingEntity;

@Entity
@Table(name = "user_credentials")
public final class UserCredentials extends BaseAuditingEntity {

	public static final int MAX_LENGTH_NAME = 50;
	public static final int MAX_LENGTH_PASSWORD = 60;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_CREDENTIALS_ID_SEQ")
	// Sequence start from 2 since the first one is populated from liquibase
	@SequenceGenerator(name = "USER_CREDENTIALS_ID_SEQ", initialValue = 2, sequenceName = "USER_CREDENTIALS_ID_SEQ", allocationSize = 1)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Size(max = 50)
	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;

	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password", nullable = false, length = 60)
	private String passwordHash;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_name", referencedColumnName = "name") })
	private Set<Role> roles = new HashSet<>();

	public UserCredentials() {
		super();

	}

	private UserCredentials(Builder builder) {
		this.name = builder.name;
		this.passwordHash = builder.passwordHash;
		setRoles(builder.roles);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public void update(String name, String passwordHash, Set<String> roles,
			Long id) {
		this.name = name;
		this.passwordHash = passwordHash;
		this.id = id;
		this.setRoles(roles);
		;
	}

	private void setRoles(Set<String> roles) {
		this.getRoles().clear();
		if (roles != null && !roles.isEmpty()) {
			for (String role : roles) {
				Role roleEntity = new Role();
				roleEntity.setName(role);
				this.getRoles().add(roleEntity);
			}
		}
	}

	public Set<String> getRolesNames() {
		Set<String> rolesNames = new HashSet<>();
		Set<Role> roles = this.getRoles();
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				rolesNames.add(role.getName());
			}
		}

		return rolesNames;

	}

	@Override
	public String toString() {
		return "UserCredentials [id=" + id + ", name=" + name
				+ ", passwordHash=" + passwordHash + ", roles=" + roles + "]";
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {
		private String name;
		private String passwordHash;
		private Set<String> roles;

		private Builder() {
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder passwordHash(String passwordHash) {
			this.passwordHash = passwordHash;
			return this;
		}

		public Builder roles(Set<String> roles) {
			this.roles = roles;
			return this;
		}

		public UserCredentials build() {
			UserCredentials build = new UserCredentials(this);
			return build;
		}
	}

}
