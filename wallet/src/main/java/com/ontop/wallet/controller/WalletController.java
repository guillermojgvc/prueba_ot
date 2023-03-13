package com.ontop.wallet.controller;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.Pattern;
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
import com.ontop.wallet.dto.wallet.ResponseBalanceDTO;
import com.ontop.wallet.dto.wallet.ResponseTransactionDTO;
import com.ontop.wallet.dto.wallet.WalletRequestDTO;
import com.ontop.wallet.service.WalletService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/wallets")
@Tag(name = "API")
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WalletController.class);
	
	@PostMapping(value = "transactions")
	@Operation(summary = "", description = "Agregar fondos al wallet")
	public ResponseEntity<ResponseTransactionDTO> walletTransaction(@Validated @RequestBody WalletRequestDTO dto){
		LOGGER.info("Mover fondos en el wallet");
		ResponseTransactionDTO result = walletService.modifyBalance(dto);
		return new ResponseEntity<>(result, result.getHttpStatus());
	}
	
	@GetMapping(value = "balance")
	@Operation(summary = "", description = "Revisar los fondos disponibles del wallet")
	public ResponseEntity<ResponseBalanceDTO> walletBalance(@RequestParam(value = "user_id") Integer userId){
		LOGGER.info("Fondos en el wallet");
		ResponseBalanceDTO result = walletService.currentBalance(userId);
		return new ResponseEntity<>(result, result.getHttpStatus());
	}
	
	@GetMapping(value = "transactions")
	@Operation(summary = "", description = "Lista las transacciones con paginación, por fecha y montos")
	public ResponseEntity<ResponsePaginatedDTO> walletListTransaction(@RequestParam(value = "user_id", required = true) Integer userId, 
																@PositiveOrZero @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
																@PositiveOrZero @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
																@PositiveOrZero @RequestParam(value = "min", required = true, defaultValue = "0.0") double val1, 
																@PositiveOrZero @RequestParam(value = "max", required = true, defaultValue = "0.0") double val2, 
																@Pattern(regexp = "yyyy-MM-dd") @RequestParam(value = "fecha_ini", required = true, defaultValue = "") Date inicio,
																@Pattern(regexp = "yyyy-MM-dd") @RequestParam(value = "fecha_fin", required = true, defaultValue = "") Date fin){
		LOGGER.info("Lista transacciones wallet");
		ResponsePaginatedDTO result = walletService.listTransactions(userId, page, size, val1, val2, new Timestamp(inicio.getTime()), new Timestamp(fin.getTime()));
		return new ResponseEntity<>(result, result.getHttpStatus());
	}

}
