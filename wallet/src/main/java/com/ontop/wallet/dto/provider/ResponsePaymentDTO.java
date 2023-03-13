package com.ontop.wallet.dto.provider;

import com.ontop.wallet.dto.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponsePaymentDTO extends ResponseDTO {
	
	private ResponseRequestInfoDTO requestInfo;
	
	private ResponsePaymentInfoDTO paymentInfo;
}
