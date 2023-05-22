package com.mts.creditapp.entity.tableEntities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tariff {
    public Tariff(String type, String interestRate){
        this.type = type;
        this.interestRate = interestRate;
    }
    private long id;
    private String type;
    private String interestRate;

}