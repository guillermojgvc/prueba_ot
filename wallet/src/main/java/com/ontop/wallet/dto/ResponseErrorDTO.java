package com.ontop.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseErrorDTO extends ResponseDTO {
	private String code;
	private String message;
}
