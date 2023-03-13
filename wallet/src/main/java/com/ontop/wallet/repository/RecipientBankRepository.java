package com.ontop.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.wallet.entities.RecipientBank;
import com.ontop.wallet.entities.User;

@Repository("recipientBankRepository")
public interface RecipientBankRepository extends JpaRepository<RecipientBank, Integer> {
	
	Optional<RecipientBank> findByAccountNumber(String accountNumber);
	
	boolean existsByAccountNumberAndTblUser(String accountNumber, User user);
	
	Page<RecipientBank> findByTblUser(User tblUser, Pageable pageable);
	
	List<RecipientBank> findByTblUserAndPrefered(User tblUser, Boolean prefered);
	
}