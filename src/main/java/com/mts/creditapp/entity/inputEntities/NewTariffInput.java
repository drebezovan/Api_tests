package com.mts.creditapp.entity.inputEntities;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewTariffInput {
    private String type;
    private String interestRate;
}
