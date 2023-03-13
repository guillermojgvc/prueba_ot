package com.ontop.wallet.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ontop.wallet.dto.ResponseDTO;
import com.ontop.wallet.dto.ResponseErrorDTO;

@RestControllerAdvice
public class ApplicationExceptionHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<ResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		LOGGER.error(ex.getMessage());
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		String mapAsString = errors.keySet().stream()
			      .map(key -> key + " " + errors.get(key))
			      .collect(Collectors.joining(", "));
		
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("INVALID_BODY");
		result.setMessage(mapAsString);
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	protected ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		LOGGER.error(ex.getMessage());
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("INVALID_BODY");
		result.setMessage("Payload not valid");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { MissingServletRequestParameterException.class })
	protected ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(MissingServletRequestParameterException ex) {
		LOGGER.error(ex.getMessage());
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("INVALID_PARAMETER");
		result.setMessage(ex.getParameterName() + " requiered");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value = { RuntimeException.class })
	protected ResponseEntity<ResponseDTO> handleRuntimeException(RuntimeException ex) {
		LOGGER.error(ex.getMessage());
		
		ex.printStackTrace();
		ResponseErrorDTO result = new ResponseErrorDTO();
		result.setCode("GENERIC_ERROR");
		result.setMessage("something bad happened");
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}