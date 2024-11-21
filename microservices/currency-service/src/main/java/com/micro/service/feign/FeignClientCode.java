package com.micro.service.feign;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.micro.exchange.model.CurrencyExchange;

@FeignClient(name = "currency-exachange-service",url="http://localhost:8004/")
public interface FeignClientCode {

    @GetMapping("/from/{from}/to/{to}")
   public CurrencyExchange retrieveExchangeValue(                        
            @PathVariable("from") String from,
            @PathVariable("to") String to);
}
