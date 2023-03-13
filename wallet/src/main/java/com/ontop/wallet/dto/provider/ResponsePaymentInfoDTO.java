package com.ontop.wallet.dto.provider;

import java.util.UUID;

import com.ontop.wallet.dto.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponsePaymentInfoDTO extends ResponseDTO {
	private Double amount;
	private UUID id;
	
}
