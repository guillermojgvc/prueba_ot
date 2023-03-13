package com.ontop.wallet.dto.bank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountRequestDTO {
	
	@JsonProperty(value = "first_name")
	@NotNull(message = "must not be null")
	private String firstName;
	
	@JsonProperty(value = "last_name")
	@NotNull(message = "must not be null")
	private String lastName;
	
	@JsonProperty(value = "routing_number")
	@NotNull(message = "must not be null")
	private String routingNumber;
	
	@JsonProperty(value = "account_number")
	@NotNull(message = "must not be null")
	private String accountNumber;
	
	@NotNull(message = "must not be null")
	private String identification;
	
	@JsonProperty(value = "bank_name")
	@NotNull(message = "must not be null")
	private String bankName;
	
	@NotNull(message = "must not be null")
	@JsonProperty(value = "user_id")
	@Min(value = 1, message = "must be greater than 1")
	private Integer userId;
	
	@NotNull(message = "must not be null")
	@JsonProperty(value = "as_default")
	private Boolean prefered;
	
}
