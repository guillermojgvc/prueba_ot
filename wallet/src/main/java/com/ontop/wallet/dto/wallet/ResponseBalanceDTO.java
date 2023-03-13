package com.ontop.wallet.dto.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ontop.wallet.dto.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseBalanceDTO extends ResponseDTO {
	private Double balance;
	@JsonProperty(value = "user_id")
	private Integer userId;
}
