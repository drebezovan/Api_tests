package com.mts.creditapp.entity.inputEntities;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DeleteCreditInput {
    private long userId;
    private UUID orderId;
}
