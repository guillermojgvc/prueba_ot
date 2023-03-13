package com.ontop.wallet.repository;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.wallet.entities.Transaction;
import com.ontop.wallet.entities.Wallet;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	Page<Transaction> findBytblWalletAndAmountBetweenAndCreatedDateBetween(Wallet wallet, double val1, double val2, Timestamp inicio, Timestamp fin, Pageable pageable);
	
}