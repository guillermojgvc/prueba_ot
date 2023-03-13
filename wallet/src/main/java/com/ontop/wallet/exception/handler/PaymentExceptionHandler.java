package com.ontop.wallet.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ontop.wallet.dto.provider.ResponseRequestInfoDTO;
import com.ontop.wallet.exception.CurrencyNotFoundException;
import com.ontop.wallet.exception.PaymentInvalidException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PaymentExceptionHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FeignPaymentExceptionHandler.class);
	
	@ExceptionHandler(value = { PaymentInvalidException.class })
	protected ResponseEntity<ResponseRequestInfoDTO> handleUserNotFoundException(PaymentInvalidException ex) {
		LOGGER.error(ex.getMessage());
		ResponseRequestInfoDTO result = new ResponseRequestInfoDTO();
		result.setError("body is invalid, check postman collection example");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { CurrencyNotFoundException.class })
	protected ResponseEntity<ResponseRequestInfoDTO> handleCurrencyNotFoundException(CurrencyNotFoundException ex) {
		LOGGER.error(ex.getMessage());
		ResponseRequestInfoDTO result = new ResponseRequestInfoDTO();
		result.setError("currency not valid, check postman collection example");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
}