package com.mts.creditapp.controller;

import constants.ErrorCode;
import constants.OrderStatus;
import constants.TariffType;
import dto.ErrorDTO;
import dto.GetStatusOrder;
import dto.TariffDTO;
import org.junit.jupiter.api.*;
import spec.CreateOrderSpec;
import spec.DeleteOrderSpec;
import spec.GetStatusOrderSpec;
import spec.GetTariffsSpec;
import utils.TableUpdater;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CreditServicesUserControllerGetAndDeleteTest {
    public static Map<String, TariffDTO> tariffMap;

    @BeforeAll
    static void getTariffsTypes() {
        tariffMap = GetTariffsSpec.getTariffsMap();
    }

    @AfterAll
    public static void cleanTable() throws FileNotFoundException {
        TableUpdater.truncateTable("loan_order");
    }

    @Tags(value = {@Tag("Smoke"), @Tag("Regress")})
    @Test
    void getStatusOrder() {
        String orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 1);
        GetStatusOrder orderStatus = GetStatusOrderSpec.getStatusOrder(orderId);
        assertEquals(OrderStatus.IN_PROGRESS.toString(), orderStatus.getOrderStatus());
    }

    @Tag("Regress")
    @Test
    void getStatusOrderAfterDecision() throws InterruptedException {
        String orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 2);
        Thread.sleep(5100);
        GetStatusOrder orderStatus = GetStatusOrderSpec.getStatusOrder(orderId);
        assertTrue(Objects.equals(OrderStatus.REFUSED.toString(), orderStatus.getOrderStatus()) || Objects.equals(OrderStatus.APPROVED.toString(), orderStatus.getOrderStatus()));
    }

    @Tag("Regress")
    @Test
    void getStatusOrderAfterUpdate() throws InterruptedException {
        String orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 3);
        Thread.sleep(5050);
        GetStatusOrder orderStatus = GetStatusOrderSpec.getStatusOrder(orderId);
        if (Objects.equals(orderStatus.getOrderStatus(), OrderStatus.APPROVED.toString())) {
            ErrorDTO error = CreateOrderSpec.createOrderError(TariffType.BUSINESS, tariffMap, 3);
            assertAll(
                    () -> assertEquals(ErrorCode.LOAN_ALREADY_APPROVED.toString(), error.getCode()),
                    () -> assertEquals("Заявка рассмотрена", error.getMessage())
            );
        } else if (Objects.equals(orderStatus.getOrderStatus(), OrderStatus.REFUSED.toString())) {
            Thread.sleep(100);
            ErrorDTO error = CreateOrderSpec.createOrderError(TariffType.BUSINESS, tariffMap, 3);
            assertAll(
                    () -> assertEquals(ErrorCode.TRY_LATER.toString(), error.getCode()),
                    () -> assertEquals("Попробуйте позже", error.getMessage())
            );
        }
    }

    @Tags(value = {@Tag("Smoke"), @Tag("Regress")})
    @Test
    void deleteOrderSuccessful() throws InterruptedException {
        String orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 4);
        Thread.sleep(5100);
        DeleteOrderSpec.deleteOrderSuccessful(4, orderId);
    }

    @Tag("Regress")
    @Test
    void deleteExistentOrderFailure() {
        String orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 5);
        ErrorDTO error = DeleteOrderSpec.deleteNonexistentOrder(5, orderId);
        assertAll(
                () -> assertEquals(ErrorCode.ORDER_IMPOSSIBLE_TO_DELETE.toString(), error.getCode()),
                () -> assertEquals("Невозможно удалить заявку", error.getMessage())
        );
    }

    @Tag("Regress")
    @Test
    void deleteNonexistentOrder() {
        String orderId = CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 6);
        ErrorDTO error = DeleteOrderSpec.deleteNonexistentOrder(0L, orderId);
        assertAll(
                () -> assertEquals(ErrorCode.ORDER_NOT_FOUND.toString(), error.getCode()),
                () -> assertEquals("Заявка не найдена", error.getMessage())
        );
    }
}