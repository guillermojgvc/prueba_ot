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
import com.ontop.wallet.exception.AccountAlreadyRegisteredException;
import com.ontop.wallet.exception.PreferedAccountNotFoundException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BankAccountExceptionHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountExceptionHandler.class);
	
	@ExceptionHandler(value = { AccountAlreadyRegisteredException.class })
	protected ResponseEntity<ResponseDTO> handleUserNotFoundException(AccountAlreadyRegisteredException ex) {
		LOGGER.error(ex.getMessage());
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("ACCOUNT_EXISTS");
		result.setMessage("The account is already registered");
		return new ResponseEntity<>(result, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { PreferedAccountNotFoundException.class })
	protected ResponseEntity<ResponseDTO> handleUserNotFoundException(PreferedAccountNotFoundException ex) {
		LOGGER.error(ex.getMessage());
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("PREFERED_ACCOUNT_NOT_FOUND");
		result.setMessage("The is not a prefered account registered");
		return new ResponseEntity<>(result, HttpStatus.CONFLICT);
	}
	
}