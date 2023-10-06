package com.estocator.controller.dto;

import com.estocator.domain.model.Company;

import javax.swing.*;

public record CompanyDto(Long id, String name, String CNPJ, String address) {

    public CompanyDto(Company model){
        this(
                model.getId(),
                model.getName(),
                model.getCNPJ(),
                model.getAddress()
        );
    }
    public Company toModel(){
        Company model = new Company();
        model.setId(this.id);
        model.setName(this.name);
        model.setCNPJ(this.CNPJ);
        model.setAddress(this.address);

        return model;
    }

}
