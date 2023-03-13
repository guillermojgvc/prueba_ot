package com.ontop.wallet.exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ontop.wallet.feign.error.FeignHttpExceptionHandler;

import feign.Response;

@Component
public class FeignPaymentException implements FeignHttpExceptionHandler {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FeignPaymentException.class);

	@Override
	public Exception handle(Response response) {
		String body;
		try {
			body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
			LOGGER.error(body);
			HttpStatus httpStatus = HttpStatus.resolve(response.status());
	        if (HttpStatus.NOT_FOUND.equals(httpStatus)) {
	        	return new ExternalURLNotFoundException();
	        }
	        if (HttpStatus.REQUEST_TIMEOUT.equals(httpStatus)) {
	        	return new ExternalURLTimeOutException();
	        }
	        if (HttpStatus.BAD_REQUEST.equals(httpStatus)) {
	        	return new PaymentInvalidException();
	        }
	        
	        return new RuntimeException(body);
		} catch (IOException e) {
			e.printStackTrace();
			return new RuntimeException();
		}
		
	}

}
