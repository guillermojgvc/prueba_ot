package com.ontop.wallet.dto.provider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
	
	@NotNull(message = "must not be null")
	private Double amount;
	
	@Valid
	@NotNull(message = "must not be null")
	private SourceDTO source;
	
	@Valid
	@NotNull(message = "must not be null")
	private DestinationDTO destination;
	
}
