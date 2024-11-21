package com.micro.exchange.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.micro.exchange.model.CurrencyExchange;
import com.micro.exchange.repository.CurrencyExchangeRepo;

@RestController
public class CurrencyExchangeController {
	@Autowired
	CurrencyExchangeRepo currencyExchangeRepo;
	
	
	@GetMapping("/from/{from}/to/{to}")
	public ResponseEntity<CurrencyExchange> retrieveExchange(@PathVariable String from,
			                                                 @PathVariable String to){
	  Optional<CurrencyExchange> optionalCurrencyExchange = currencyExchangeRepo.findByFromCurrencyAndToCurrency(from, to);
	  if(optionalCurrencyExchange.isPresent()) {

		  CurrencyExchange exchange= optionalCurrencyExchange.get();
		  return ResponseEntity.status(HttpStatus.OK)
				               .header("status", "success")
				               .body(exchange);
	  }
	  else {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND)
				  			   .header("status", "Not found")
				  			   .body(null);
	  }
	}

}
