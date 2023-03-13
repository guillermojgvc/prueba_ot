package com.ontop.wallet.service;

import java.sql.Timestamp;

import com.ontop.wallet.dto.ResponsePaginatedDTO;
import com.ontop.wallet.dto.wallet.ResponseBalanceDTO;
import com.ontop.wallet.dto.wallet.ResponseTransactionDTO;
import com.ontop.wallet.dto.wallet.WalletRequestDTO;

public interface WalletService {
	
	ResponseTransactionDTO modifyBalance(WalletRequestDTO dto);
	
	ResponseBalanceDTO currentBalance(Integer userId);
	
	ResponsePaginatedDTO listTransactions(int userId, int pageNumber, int pageSize, double val1, double val2, Timestamp inicio, Timestamp fin);
	
}