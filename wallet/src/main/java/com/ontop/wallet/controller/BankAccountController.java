package com.ontop.wallet.controller;

import javax.validation.constraints.PositiveOrZero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ontop.wallet.dto.ResponsePaginatedDTO;
import com.ontop.wallet.dto.bank.BankAccountRequestDTO;
import com.ontop.wallet.dto.bank.ResponseBankAccountDTO;
import com.ontop.wallet.service.BankAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/recipient_acounts")
@Tag(name = "API")
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);
	
	@PostMapping(value = "/add")
	@Operation(summary = "", description = "Agregar cuentas al wallet, considerar el valor de prefered como cuenta preferida")
	public ResponseEntity<ResponseBankAccountDTO> bankAdd(@Validated @RequestBody BankAccountRequestDTO dto){
		LOGGER.info("Agregar cuentas");
		ResponseBankAccountDTO result = bankAccountService.addBankAccount(dto);
		return new ResponseEntity<>(result, result.getHttpStatus());
	}
	
	@GetMapping(value = "accounts")
	@Operation(summary = "", description = "Listar cuentas disponibles")
	public ResponseEntity<ResponsePaginatedDTO> listAccounts(@RequestParam(value = "user_id", required = true) Integer userId, 
																@PositiveOrZero @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
																@PositiveOrZero @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
		LOGGER.info("Fondos en el wallet");
		ResponsePaginatedDTO result = bankAccountService.listBankAccount(userId, page, size);
		return new ResponseEntity<>(result, result.getHttpStatus());
	}

}
