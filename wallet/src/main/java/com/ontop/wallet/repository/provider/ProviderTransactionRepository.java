package com.ontop.wallet.repository.provider;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.wallet.entities.provider.ProviderTransaction;

@Repository("providerTransactionRepository")
public interface ProviderTransactionRepository extends JpaRepository<ProviderTransaction, UUID> {
	
}