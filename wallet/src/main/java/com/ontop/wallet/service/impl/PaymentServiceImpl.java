package com.ontop.wallet.service.impl;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ontop.wallet.dto.provider.PaymentRequestDTO;
import com.ontop.wallet.dto.provider.ResponsePaymentDTO;
import com.ontop.wallet.dto.provider.ResponsePaymentInfoDTO;
import com.ontop.wallet.dto.provider.ResponseRequestInfoDTO;
import com.ontop.wallet.dto.provider.ResponseStatusDTO;
import com.ontop.wallet.entities.provider.ProviderTransaction;
import com.ontop.wallet.repository.provider.ProviderTransactionRepository;
import com.ontop.wallet.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private ProviderTransactionRepository providerTransactionRepository;

	@Transactional
	@Override
	public ResponsePaymentDTO createPayment(PaymentRequestDTO dto) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ResponsePaymentDTO result = new ResponsePaymentDTO();
		ProviderTransaction transaction = new ProviderTransaction();
		
		transaction.setId(UUID.randomUUID());
		transaction.setStatus("Processing");
		transaction.setCreatedDate(timestamp);
		transaction.setUpdatedDate(timestamp);
		
		providerTransactionRepository.save(transaction);
		
		ResponsePaymentInfoDTO infoDTO = new ResponsePaymentInfoDTO();
		infoDTO.setAmount(dto.getAmount());
		infoDTO.setId(transaction.getId());
		
		ResponseRequestInfoDTO requestInfoDTO = new ResponseRequestInfoDTO();
		requestInfoDTO.setStatus(transaction.getStatus());
		
		result.setHttpStatus(HttpStatus.OK);
		result.setPaymentInfo(infoDTO);
		result.setRequestInfo(requestInfoDTO);

		return result;
	}
	

	@Override
	public ResponseStatusDTO currentStatus(UUID transactionId) {
		ResponseStatusDTO result = new ResponseStatusDTO();
		Optional<ProviderTransaction> optProviderTransaction = providerTransactionRepository.findById(transactionId);
		ProviderTransaction providerTransaction = optProviderTransaction.orElseThrow(()->new RuntimeException());
		
		result.setHttpStatus(HttpStatus.OK);
		result.setDate(providerTransaction.getUpdatedDate().toString());
		result.setStatus(providerTransaction.getStatus());
				
		return result;
	}

}
