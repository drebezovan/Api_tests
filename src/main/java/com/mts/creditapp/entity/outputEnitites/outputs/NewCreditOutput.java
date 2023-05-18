package com.mts.creditapp.entity.outputEnitites.outputs;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class NewCreditOutput {
    private UUID orderId;
}
