package com.ontop.wallet.dto.wallet;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ontop.wallet.dto.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class ResponseTransactionDTO extends ResponseDTO {
	@JsonProperty(value = "wallet_transaction_id")
	private Integer walletTransactionId;
	private Double amount;
	@JsonProperty(value = "user_id")
	private Integer userId;
	
	private Timestamp date;
	@JsonProperty(value = "transaction_id")
	private UUID transactionId;
}
