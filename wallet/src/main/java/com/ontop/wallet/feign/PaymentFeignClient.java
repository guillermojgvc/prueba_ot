package com.ontop.wallet.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ontop.wallet.dto.provider.PaymentRequestDTO;
import com.ontop.wallet.dto.provider.ResponsePaymentDTO;
import com.ontop.wallet.exception.FeignPaymentException;
import com.ontop.wallet.exception.NotEnoughFundException;
import com.ontop.wallet.feign.error.HandleFeignException;

@FeignClient(value="payment-provider-client", url="${ontop.payment.url}")
public interface PaymentFeignClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "/payments", consumes = "application/json", produces = "application/json")
	@HandleFeignException(FeignPaymentException.class)
	ResponsePaymentDTO createPayment(@RequestBody PaymentRequestDTO dto);
	
}