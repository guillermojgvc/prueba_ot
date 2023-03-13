package com.ontop.wallet.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ontop.wallet.dto.ResponseDTO;
import com.ontop.wallet.dto.ResponseErrorDTO;
import com.ontop.wallet.exception.NotEnoughFundException;
import com.ontop.wallet.exception.UserNotFoundException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WalletExceptionHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WalletExceptionHandler.class);
	
	@ExceptionHandler(value = { UserNotFoundException.class })
	protected ResponseEntity<ResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
		LOGGER.error(ex.getMessage());
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("INVALID_USER");
		result.setMessage("user not found");
		return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { NotEnoughFundException.class })
	protected ResponseEntity<ResponseDTO> handleUserNotFoundException(NotEnoughFundException ex) {
		LOGGER.error(ex.getMessage());
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("NOT_ENOUGH_FUNDS");
		result.setMessage("user not have enough funds");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	
}