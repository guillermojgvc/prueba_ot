package com.ontop.wallet.exception.handler;

import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ontop.wallet.dto.provider.ResponseRequestInfoDTO;
import com.ontop.wallet.exception.ExternalURLNotFoundException;
import com.ontop.wallet.exception.ExternalURLTimeOutException;
import com.ontop.wallet.exception.PaymentInvalidException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FeignPaymentExceptionHandler{
	
private static final Logger LOGGER = LoggerFactory.getLogger(PaymentExceptionHandler.class);
	
	@ExceptionHandler(value = { ExternalURLNotFoundException.class })
	protected ResponseEntity<ResponseRequestInfoDTO> handleExternalURLNotFoundException(ExternalURLNotFoundException ex) {
		LOGGER.error(ex.getMessage());
		ResponseRequestInfoDTO result = new ResponseRequestInfoDTO();
		result.setError("Requested url not found");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { ExternalURLTimeOutException.class })
	protected ResponseEntity<ResponseRequestInfoDTO> handleExternalURLTimeOutException(ExternalURLTimeOutException ex) {
		LOGGER.error(ex.getMessage());
		ResponseRequestInfoDTO result = new ResponseRequestInfoDTO();
		result.setError("Timeout");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { SocketTimeoutException.class })
	protected ResponseEntity<ResponseRequestInfoDTO> handleSocketTimeoutException(SocketTimeoutException ex) {
		LOGGER.error(ex.getMessage());
		ResponseRequestInfoDTO result = new ResponseRequestInfoDTO();
		result.setError("Timeout");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { PaymentInvalidException.class })
	protected ResponseEntity<ResponseRequestInfoDTO> handlePaymentInvalidException(PaymentInvalidException ex) {
		LOGGER.error(ex.getMessage());
		ResponseRequestInfoDTO result = new ResponseRequestInfoDTO();
		result.setError("Something goes bad, check user account prefered and try again");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
}