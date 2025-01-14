package com.micro.service.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.micro.exchange.model.CurrencyExchange;
import com.micro.service.feign.FeignClientCode;
import com.micro.service.model.CurrencyServiceModel;
import com.micro.service.repository.CurrencyServiceRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CurrencyServiceController {

    @Autowired
    CurrencyServiceRepository currencyServiceRepository;
    
    @Autowired
    FeignClientCode feignClientCode;

    @GetMapping("/from/{currencyfrom}/to/{currencyTo}/quantity/{quantity}")
    @CircuitBreaker(name = "currencyService", fallbackMethod = "currencyExchangeFallback")
    public ResponseEntity<CurrencyServiceModel> calculateCurrencyConversion(
									        @PathVariable String currencyfrom,
									        @PathVariable String currencyTo,
									        @PathVariable Long quantity) {
	
    	
    	//creating object for model class to set the data
	        CurrencyServiceModel currency = new CurrencyServiceModel();
	        
	    //calling the feignClient method by passing required fields(from and to) and it return the object
	        CurrencyExchange currencyExchange = feignClientCode.retrieveExchangeValue(currencyfrom, currencyTo);
	     
	    //get the required value and set the data in to the currency (Model objects)
	        Double currencyMultiple= currencyExchange.getConversionMultiple();
	        
	        
	        currency.setCurrencyMultiple(currencyMultiple);
	        currency.setCurrencyTo(currencyTo);
	        currency.setCurrencyfrom(currencyfrom);
	        currency.setQuantity(quantity);
	        double total = currencyMultiple * quantity;
	        currency.setTotal(total);
	        currency.setDate(LocalDate.now());
	        currency.setTime(LocalDateTime.now());
       
        
        CurrencyServiceModel saveCurrency = currencyServiceRepository.save(currency);

        return ResponseEntity.status(HttpStatus.OK).body(saveCurrency);
    }
    

	public ResponseEntity<CurrencyServiceModel> currencyExchangeFallback(
	        String from, String to, double quantity, Throwable ex) {

	    // Log the exception if needed
	    System.out.println("Fallback triggered for from = " + from + ", to = " + to + " due to " + ex.getMessage());

	    // Provide a default response or a custom fallback response
	    CurrencyExchange fallbackResponse = new CurrencyExchange();
	    fallbackResponse.setFromCurrency(from);
	    fallbackResponse.setToCurrency(to);
	    fallbackResponse.setConversionMultiple(0.0);  // Default value if the service is down
	    
	    CurrencyServiceModel currencyConversion = new CurrencyServiceModel();
	    currencyConversion.setCurrencyfrom(from);
	    currencyConversion.setCurrencyTo(to);
	    currencyConversion.setQuantity(quantity);
	    currencyConversion.setCurrencyMultiple(fallbackResponse.getConversionMultiple());
	    currencyConversion.setTotal(quantity * fallbackResponse.getConversionMultiple());
	    currencyConversion.setDate(LocalDate.now());
        currencyConversion.setTime(LocalDateTime.now());
   
	    
	    // You can optionally log or save the failed conversion if needed
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .header("value", "Fallback: currency conversion failed due to service unavailability")
	            .body(currencyConversion);
	}
	  

}