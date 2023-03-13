package com.ontop.wallet.dto;

import java.util.List;

import com.ontop.wallet.dto.bank.ResponseBankAccountDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponsePaginatedDTO extends ResponseDTO {
	
	private List<ResponseDTO> elements;
	private int currentPage;
	private long totalElements;
	private long totalPages;
	
}
