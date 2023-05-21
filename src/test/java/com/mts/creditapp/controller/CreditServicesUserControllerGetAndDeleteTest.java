package com.mts.creditapp.controller;

import com.mts.creditapp.entity.tableEntities.Tariff;
import constants.ErrorCode;
import constants.OrderStatus;
import constants.TariffType;
import dto.ErrorDTO;
import dto.GetStatusOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spec.CreateOrderSpec;
import spec.DeleteOrderSpec;
import spec.GetStatusOrderSpec;
import spec.GetTariffsSpec;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CreditServicesUserControllerGetAndDeleteTest {
    public static Map<String, Tariff> tariffMap;
    public String orderId;
    long userCounter = 1;

    @BeforeAll
    static void getTariffsTypes() {
        tariffMap = GetTariffsSpec.getTariffsMap();
    }

    @BeforeEach
    @DisplayName("Предусловие с созданием заявки")
    public void initOrder() {
        orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, userCounter);
        userCounter++;
    }

    @Test
    void getStatusOrder() {
       GetStatusOrder orderStatus = GetStatusOrderSpec.getStatusOrder(orderId);
       assertEquals(OrderStatus.IN_PROGRESS.toString(), orderStatus.getOrderStatus());
    }
    @Test
    void getStatusOrderAfterDecision() throws InterruptedException {
        Thread.sleep(3000);
       GetStatusOrder orderStatus = GetStatusOrderSpec.getStatusOrder(orderId);
       assertTrue(Objects.equals(OrderStatus.REFUSED.toString(), orderStatus.getOrderStatus()) || Objects.equals(OrderStatus.APPROVED.toString(), orderStatus.getOrderStatus()));
    }

    @Test
    void deleteOrderSuccessful() throws InterruptedException {
        Thread.sleep(3000);
        DeleteOrderSpec.deleteOrderSuccessful(userCounter, orderId);
    }
    @Test
    void deleteExistentOrderFailure(){
        ErrorDTO error = DeleteOrderSpec.deleteNonexistentOrder(userCounter, orderId);
        assertAll(
                ()-> assertEquals(ErrorCode.ORDER_IMPOSSIBLE_TO_DELETE.toString(), error.getCode()),
                () -> assertEquals("Невозможно удалить заявку", error.getMessage())
        );
    }
    @Test
    void deleteNonexistentOrder(){
        ErrorDTO error = DeleteOrderSpec.deleteNonexistentOrder(0L, orderId);
        assertAll(
                ()-> assertEquals(ErrorCode.ORDER_NOT_FOUND.toString(), error.getCode()),
                () -> assertEquals("Заявка не найдена", error.getMessage())
        );
    }

}