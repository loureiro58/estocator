package com.estocator.domain.repository;

import com.estocator.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    boolean existsByCompanyCNPJ(String CNPJ);

}
