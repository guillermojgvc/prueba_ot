package com.ontop.wallet.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ResponseDTO {
	@JsonIgnore
	private HttpStatus httpStatus;
}
