package com.micro.exchange.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchange {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String fromCurrency;
	private String toCurrency;
	private double conversionMultiple;
	public Double getCurrencyMultiple() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
