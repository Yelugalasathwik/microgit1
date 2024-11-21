package com.micro.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.service.model.CurrencyServiceModel;

@Repository
public interface CurrencyServiceRepository extends JpaRepository<CurrencyServiceModel, Long> {

}
