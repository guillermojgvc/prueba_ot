package com.ontop.wallet.service;

import java.util.UUID;

import com.ontop.wallet.dto.provider.PaymentRequestDTO;
import com.ontop.wallet.dto.provider.ResponsePaymentDTO;
import com.ontop.wallet.dto.provider.ResponseStatusDTO;

public interface PaymentService {
	
	ResponsePaymentDTO createPayment(PaymentRequestDTO dto);
	
	ResponseStatusDTO currentStatus(UUID transactionId);
	
}