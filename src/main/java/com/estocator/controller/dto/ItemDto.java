package com.estocator.controller.dto;

import com.estocator.domain.model.Item;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public record ItemDto(Long id, String code, String name, int amount, BigDecimal price, String location) {

    public ItemDto(Item model){
        this(
                model.getId(),
                model.getCode(),
                model.getName(),
                model.getAmount(),
                model.getPrice(),
                model.getLocation()
        );
    }

    public Item toModel(){
        Item item = new Item();
        item.setId(this.id);
        item.setCode(this.code);
        item.setName(this.name);
        item.setAmount(this.amount);
        item.setPrice(this.price);
        item.setLocation(this.location);

        return item;
    }

}
