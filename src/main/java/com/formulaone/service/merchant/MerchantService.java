package com.formulaone.service.merchant;

import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.MerchantResponse;

public interface MerchantService {

	public MerchantResponse createMerchant(MerchantRequest request);


}