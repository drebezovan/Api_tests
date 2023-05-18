package com.mts.creditapp.service;


import com.mts.creditapp.entity.inputEntities.DeleteCreditInput;
import com.mts.creditapp.entity.inputEntities.NewCreditInput;
import com.mts.creditapp.entity.inputEntities.NewTariffInput;
import com.mts.creditapp.entity.outputEnitites.outputs.GenericOutput;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CreditAdminServices {
    int newTariff(NewTariffInput newTariffInput);
    GenericOutput getAllOrders();

}
