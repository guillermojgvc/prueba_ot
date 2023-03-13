package com.ontop.wallet.dto.provider;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceInformationDTO {
	
	@NotNull(message = "must not be null")
	private String name;
	
}
