package com.mts.creditapp.service.impl;

import com.mts.creditapp.entity.inputEntities.DeleteCreditInput;
import com.mts.creditapp.entity.inputEntities.NewCreditInput;
import com.mts.creditapp.entity.inputEntities.NewTariffInput;
import com.mts.creditapp.entity.outputEnitites.outputs.GenericOutput;
import com.mts.creditapp.entity.outputEnitites.outputs.GetAllTariffsOutput;
import com.mts.creditapp.entity.outputEnitites.outputs.GetOrderStatusOutput;
import com.mts.creditapp.entity.outputEnitites.outputs.NewCreditOutput;
import com.mts.creditapp.entity.tableEntities.LoanOrder;
import com.mts.creditapp.entity.tableEntities.Tariff;
import com.mts.creditapp.exception.customException.CreditException;
import com.mts.creditapp.repository.LoanOrderRep;
import com.mts.creditapp.repository.TariffRep;
import com.mts.creditapp.service.CreditUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditUserServicesImpl implements CreditUserServices {
    @Value("${fixedRate:120000}")
    private int fixedRate;
    @Autowired
    private final TariffRep tariffRep;
    @Autowired
    private final LoanOrderRep loanOrderRep;
    @Override
    public GenericOutput getAllTariffs() {
        return new GenericOutput(new GetAllTariffsOutput(tariffRep.getAllTariffs()));
    }

    @Override
    public GenericOutput newCredit(NewCreditInput newCreditInput){
        if (!tariffRep.checkIfTariffExists(newCreditInput.getTariffId()).get()){
            throw new CreditException("TARIFF_NOT_FOUND", "Тариф не найден");
        }
        List<LoanOrder> loanOrderList =
                loanOrderRep.findAllLoanOrderByUserId(newCreditInput.getUserId()).orElseThrow(
                        () -> new CreditException("SOMETHING_WENT_WRONG","Что-то пошло не так")
                );
        for (int i=0; i<loanOrderList.size(); ++i){
            if (loanOrderList.get(i).getTariffId()== newCreditInput.getTariffId()){
                if (loanOrderList.get(i).getStatus().compareTo("IN_PROGRESS")==0) {
                    throw new CreditException("LOAN_CONSIDERATION", "Заявка рассматривается");
                }
                else if (loanOrderList.get(i).getStatus().compareTo("APPROVED")==0) {
                    throw new CreditException("LOAN_ALREADY_APPROVED", "Заявка рассмотрена");
                }
                else if (loanOrderList.get(i).getStatus().compareTo("REFUSED")==0
                        && new Timestamp(System.currentTimeMillis()).getTime()
                        -loanOrderList.get(i).getTimeUpdate().getTime()<=fixedRate) {
                    throw new CreditException("TRY_LATER", "Попробуйте позже");
                }
            }
        }
        return new GenericOutput(
                new NewCreditOutput(
                        loanOrderRep.addLoanOrder(
                                new LoanOrder(UUID.randomUUID().toString(),
                                        newCreditInput.getUserId(),
                                        newCreditInput.getTariffId(),
                                        0.1+Math.random()*0.8,
                                        "IN_PROGRESS",
                                        new Timestamp(System.currentTimeMillis()),
                                        new Timestamp(System.currentTimeMillis())))));
    }

    @Override
    public GenericOutput getOrderStatus(UUID orderId){
        return new GenericOutput(
                new GetOrderStatusOutput(
                        loanOrderRep.getOrderStatusById(orderId).orElseThrow(
                                () -> new CreditException("NO_ORDER_PRESENT","Нет заявки, соответствующей данному ID"))));
    }
    @Override
    public int deleteOrder(DeleteCreditInput deleteCreditInput) {
        if (!loanOrderRep.checkExistOrderByUserIdAndOrderId(
                deleteCreditInput.getUserId(),
                deleteCreditInput.getOrderId())){
            throw new CreditException("ORDER_NOT_FOUND", "Заявка не найдена");
        }
        else {
            if (!loanOrderRep.getOrderStatusById(
                    deleteCreditInput.getOrderId())
                    .get().equals("IN_PROGRESS")) {
                return loanOrderRep.deleteLoanOrder(deleteCreditInput.getOrderId(), deleteCreditInput.getUserId());
            } else {
                throw new CreditException("ORDER_IMPOSSIBLE_TO_DELETE", "Заявку невозможно удалить");
            }
        }
    }

}
