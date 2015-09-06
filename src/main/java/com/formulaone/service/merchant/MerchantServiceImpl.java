package com.formulaone.service.merchant;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.MerchantResponse;
import com.formulaone.controller.exceptions.MerchantNotFoundException;
import com.formulaone.domain.merchant.Merchant;
import com.formulaone.repository.merchant.MerchantRepository;

@Service
public class MerchantServiceImpl implements MerchantService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MerchantRepository repository;

	@Autowired
	public MerchantServiceImpl(MerchantRepository repository) {
		this.repository = repository;
	}

	@Transactional
	@Override
	public MerchantResponse createMerchant(MerchantRequest request) {
		Merchant merchant = new Merchant.MerchantEntityBuilder(request).build();
		merchant = this.repository.save(merchant);
		return MerchantDTOMapper.map(merchant);
		

	}

	@Transactional
	@Override
	public void delete(Long id) {
		logger.debug("Deleting merchant entry with id: {}", id);
		Merchant deleted = getMerchantById(id);
		repository.delete(deleted);
		logger.debug("Deleted successfully merchant entry with information: {}",
				deleted);
	}

	@Override
	public MerchantResponse findMerchantById(Long id) {
		logger.debug("Finding merchant entry with id: {}", id);
		Merchant merchant = this.getMerchantById(id);
		logger.debug("Found merchant entry with information: {}", merchant);
		return MerchantDTOMapper.map(merchant);
	}

	@Override
	public List<MerchantResponse> findall() {
		List<Merchant> merchants = this.repository.findAll();
		return MerchantDTOMapper.map(merchants);
	}

	private Merchant getMerchantById(Long id) {
		Optional<Merchant> result = Optional.ofNullable(repository.findOne(id));
		return result.orElseThrow(() -> new MerchantNotFoundException(id));

	}

}