package com.ontop.wallet.dto.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ontop.wallet.dto.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class ResponseRequestInfoDTO extends ResponseDTO {
	private String status;
	private String error;
	
}
