package com.formulaone.service.merchant;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.domain.merchant.Merchant;
import com.formulaone.repository.merchant.MerchantRepository;

@Service
public class MerchantServiceImpl implements MerchantService {

	private MerchantRepository repository;

	@Autowired
	public MerchantServiceImpl(MerchantRepository repository) {
		this.repository = repository;
	}

	@Transactional
	@Override
	public MerchantResponse createMerchant(MerchantRequest request) {
		Merchant merchant = new Merchant(request.getName());
		merchant = this.repository.save(merchant);
		MerchantResponse merchantResponse = new MerchantResponse();
		merchantResponse.setRequestId(merchant.getId());
		merchantResponse.setCompanyName(merchant.getName());
		return merchantResponse;

	}

	@Override
	public List<MerchantResponse> findall() {
		List<Merchant> merchants = this.repository.findAll();
		
		List<MerchantResponse> allMerchants = new ArrayList<>();
		for(Merchant merchant : merchants) {
			MerchantResponse resp = new MerchantResponse();
			resp.setCompanyName(merchant.getName());
			resp.setRequestId(merchant.getId());
			allMerchants.add(resp);
		}
		
		return allMerchants;
	}

}