package com.mts.creditapp.repository;

import com.mts.creditapp.entity.tableEntities.LoanOrder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanOrderRep {
    Optional<List> getAllLoanOrder();
    Optional<List> findAllLoanOrderByUserId(long userId);
    UUID addLoanOrder(LoanOrder loanOrder);
    Optional<String> getOrderStatusById(UUID orderId);
    int deleteLoanOrder(UUID orderId, long userId);
    boolean checkExistOrderByUserIdAndOrderId(long userId, UUID orderId);
    int updateOrderStatus(long id, String status);
    int updateTimeUpdate(long id, Timestamp timeUpdate);
    Optional<List> getAllOrdersWithStatus(String status);
    void deleteAllOrders();
}
