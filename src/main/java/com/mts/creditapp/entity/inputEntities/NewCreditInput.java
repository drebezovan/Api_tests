package com.mts.creditapp.entity.inputEntities;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewCreditInput {
    private long userId;
    private long tariffId;
}
