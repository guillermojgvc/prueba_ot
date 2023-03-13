package com.ontop.wallet.dto.provider;

import java.sql.Timestamp;

import com.ontop.wallet.dto.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseStatusDTO extends ResponseDTO{
	private String status;
	private String date;
}
