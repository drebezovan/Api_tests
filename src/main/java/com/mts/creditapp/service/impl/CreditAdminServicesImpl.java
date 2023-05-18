package com.mts.creditapp.service.impl;

import com.mts.creditapp.entity.inputEntities.NewTariffInput;
import com.mts.creditapp.entity.outputEnitites.outputs.GenericOutput;
import com.mts.creditapp.entity.tableEntities.Tariff;
import com.mts.creditapp.repository.LoanOrderRep;
import com.mts.creditapp.repository.TariffRep;
import com.mts.creditapp.service.CreditAdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditAdminServicesImpl implements CreditAdminServices {
    @Autowired
    private final TariffRep tariffRep;
    @Autowired
    private final LoanOrderRep loanOrderRep;
    @Override
    public GenericOutput getAllOrders(){
        return new GenericOutput(loanOrderRep.getAllLoanOrder());
    }
    @Override
    public int newTariff(NewTariffInput newTariffInput){
        return tariffRep.addTariff(new Tariff(newTariffInput.getType(), newTariffInput.getInterestRate()));
    }
}
