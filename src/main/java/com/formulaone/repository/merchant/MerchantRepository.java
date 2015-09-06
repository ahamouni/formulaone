package com.formulaone.repository.merchant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formulaone.domain.merchant.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
