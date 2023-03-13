package com.ontop.wallet.service;

import com.ontop.wallet.dto.ResponsePaginatedDTO;
import com.ontop.wallet.dto.bank.BankAccountRequestDTO;
import com.ontop.wallet.dto.bank.ResponseBankAccountDTO;

public interface BankAccountService {	
	
	ResponseBankAccountDTO addBankAccount(BankAccountRequestDTO dto);
	
	ResponsePaginatedDTO listBankAccount(int userId,int pageNumber, int pageSize);
}