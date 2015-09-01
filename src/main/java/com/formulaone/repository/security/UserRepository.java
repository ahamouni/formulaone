package com.formulaone.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formulaone.domain.security.UserCredentials;

/**
 * This repository provides CRUD operations for {@link com.formulaone.api.domain.security.UserCredentials}
 * objects.
 * @author 
 */

@Repository
public  interface UserRepository extends  JpaRepository<UserCredentials, Long> {

	Optional<UserCredentials> findOneByName(String name);

}
