package com.ontop.wallet.dto.bank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ontop.wallet.dto.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponseBankAccountDTO extends ResponseDTO {
	
	@JsonProperty(value = "bank_account_id")
	private Integer bankAccountId;
	
	@JsonProperty(value = "bank_name")
	private String bankName;
	
	@JsonProperty(value = "bank_account_number")
	private String bankAccountNumber;
	
	@JsonProperty(value = "as_default")
	private Boolean prefered;
	
}
