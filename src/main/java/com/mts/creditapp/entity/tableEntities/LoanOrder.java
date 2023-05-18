package com.mts.creditapp.entity.tableEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class LoanOrder {
    public LoanOrder(String orderId,
                     long userId,
                     long tariffId,
                     double creditRating,
                     String status,
                     Timestamp timeInsert,
                     Timestamp timeUpdate){
        this.orderId = orderId;
        this.userId = userId;
        this.tariffId = tariffId;
        this.creditRating = creditRating;
        this.status = status;
        this.timeInsert= timeInsert;
        this.timeUpdate = timeUpdate;
    }
    private long id;
    private String orderId;
    private long userId;
    private long tariffId;
    private double creditRating;
    private String status;
    private Timestamp timeInsert;
    private Timestamp timeUpdate;
}
