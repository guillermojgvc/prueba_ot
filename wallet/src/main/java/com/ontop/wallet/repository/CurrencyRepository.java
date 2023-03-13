package com.ontop.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.wallet.entities.Currency;

@Repository("currencyRepository")
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
	
	Optional<Currency> findByCurrencyAbbreviate(String currencyAbbreviate);
	
}