package com.estocator.controller.dto;

import com.estocator.domain.model.Company;
import com.estocator.domain.model.Item;
import com.estocator.domain.model.Stock;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record StockDto(Long id, CompanyDto company, List<ItemDto> itens){

    public StockDto(Stock model) {
        this(
                model.getId(),
                ofNullable(model.getCompany()).map(CompanyDto::new).orElse(null),
                ofNullable(model.getItens()).orElse(emptyList()).stream().map(ItemDto::new).collect(toList())
        );
    }

    public Stock toModel(){
        Stock stock = new Stock();
        stock.setId(this.id);
        stock.setCompany(ofNullable(this.company).map(CompanyDto::toModel).orElse(null));
        stock.setItens(ofNullable(this.itens).orElse(emptyList()).stream().map(ItemDto::toModel).collect(toList()));

        return stock;
    }
}
