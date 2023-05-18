package com.mts.creditapp.service.impl;

import com.mts.creditapp.entity.tableEntities.LoanOrder;
import com.mts.creditapp.repository.LoanOrderRep;
import com.mts.creditapp.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class JobServiceImpl implements JobService {
    @Autowired
    private LoanOrderRep loanOrderRep;
    @Async
    @Scheduled(fixedRate = 120000, initialDelay = 10000)
    public void reviewOrder(){
        List<Long> ordersWithInProgressStatus =  loanOrderRep.getAllOrdersWithStatus("IN_PROGRESS").get();
        for (int i=0; i<ordersWithInProgressStatus.size(); i++){
            if (Math.random()>0.5){
                loanOrderRep.updateOrderStatus(
                        ordersWithInProgressStatus.get(i),
                        "APPROVED");
            }
            else {
                loanOrderRep.updateOrderStatus(
                        ordersWithInProgressStatus.get(i),
                        "REFUSED");
            }
            loanOrderRep.updateTimeUpdate(
                    ordersWithInProgressStatus.get(i),
                    new Timestamp(System.currentTimeMillis()));
        }
    }
}
