package com.ontop.wallet.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ontop.wallet.dto.provider.PaymentRequestDTO;
import com.ontop.wallet.dto.provider.ResponsePaymentDTO;
import com.ontop.wallet.dto.provider.ResponseStatusDTO;
import com.ontop.wallet.exception.PaymentInvalidException;
import com.ontop.wallet.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1/payments")
@Tag(name = "PROVIDER API")
public class PaymentsController {
	
	@Autowired
	private PaymentService paymentService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsController.class);
	
	@PostMapping(value = "")
	@Operation(summary = "", description = "Gestionar pago por provider externo")
	public ResponseEntity<ResponsePaymentDTO> walletTransaction(@Validated @RequestBody PaymentRequestDTO dto, BindingResult errors){
		LOGGER.info("Transacciones proveedor");
		if(errors.hasErrors()) {
			throw new PaymentInvalidException();
		}
		ResponsePaymentDTO result = paymentService.createPayment(dto);
		return new ResponseEntity<>(result, result.getHttpStatus());
	}
	
	@GetMapping(value = "/{paymentId}/status")
	@Operation(summary = "", description = "Consultar el status en proveedor externo")
	public ResponseEntity<ResponseStatusDTO> walletTransaction(@PathVariable String paymentId){
		LOGGER.info("Consulta status desde provider");
		ResponseStatusDTO result = paymentService.currentStatus(UUID.fromString(paymentId));
		return new ResponseEntity<>(result, result.getHttpStatus());
	}

}
