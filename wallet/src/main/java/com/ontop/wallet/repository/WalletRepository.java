package com.ontop.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.wallet.entities.User;
import com.ontop.wallet.entities.Wallet;

@Repository("walletRepository")
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	
	Optional<Wallet> findByTblUser(User tblUser);
	
	Optional<Wallet> findByAccountNumber(String account);
	
}