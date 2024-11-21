package com.micro.exchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.exchange.model.CurrencyExchange;

@Repository
public interface CurrencyExchangeRepo extends JpaRepository<CurrencyExchange, Long> {
		
	Optional<CurrencyExchange> findByFromCurrencyAndToCurrency(String from, String to);
}
