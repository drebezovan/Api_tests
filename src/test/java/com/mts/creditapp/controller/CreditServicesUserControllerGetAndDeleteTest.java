package com.mts.creditapp.controller;

import com.mts.creditapp.entity.tableEntities.Tariff;
import constants.OrderStatus;
import constants.TariffType;
import dto.GetStatusOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spec.CreateOrderSpec;
import spec.GetStatusOrderSpec;
import spec.GetTariffsSpec;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CreditServicesUserControllerGetAndDeleteTest extends BaseTest{
    public static Map<String, Tariff> tariffMap;
    public String orderId;

    @BeforeAll
    static void getTariffsTypes() {
        tariffMap = GetTariffsSpec.getTariffsMap();
    }

    @BeforeEach
    @DisplayName("Предусловие с созданием заявки")
    public void initOrder() {
        orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 2L);
    }

    @Test
    void getStatusOrder() {
       GetStatusOrder orderStatus = GetStatusOrderSpec.getStatusOrder(orderId);
       assertEquals(OrderStatus.IN_PROGRESS.toString(), orderStatus.getOrderStatus());
    }

    @Test
    void deleteOrder() {
    }
}