package com.ontop.wallet.dto.wallet;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletRequestDTO {
	
	@NotNull(message = "must not be null")
	@JsonProperty(value = "amount")
	private Double amount;
	
	@NotNull(message = "must not be null")
	@JsonProperty(value = "user_id")
	@Min(value = 1, message = "must be greater than 1")
	private Integer userId;
	
}
