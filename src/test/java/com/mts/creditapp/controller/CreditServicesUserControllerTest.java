package com.mts.creditapp.controller;

import com.mts.creditapp.entity.tableEntities.Tariff;
import constants.ErrorCode;
import constants.TariffType;
import dto.ErrorDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import spec.CreateOrderSpec;
import spec.GetTariffsSpec;
import utils.TableUpdater;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
class CreditServicesUserControllerTest {

    public static Map<String, Tariff> tariffMap;
    static int userCounter = 100;

    @BeforeAll
    static void getTariffsTypesAndCreateUser() {
        tariffMap = GetTariffsSpec.getTariffsMap();
        CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, userCounter);
    }
    @AfterAll
    public static void cleanTable() throws FileNotFoundException {
        TableUpdater.truncateTable("loan_order");
    }
    @AfterEach
    public void updateCounter(){
        userCounter++;
    }
    @Test
    void addAlreadyExistingOrder() {
        ErrorDTO errorDTO = CreateOrderSpec.createOrderError(TariffType.CONSUMER, tariffMap, 100);
        assertAll(
                () -> assertEquals(ErrorCode.LOAN_CONSIDERATION.toString(), errorDTO.getCode()),
                () -> assertEquals("Заявка рассматривается", errorDTO.getMessage())
        );
    }
    @Test
    void getTariffs() throws IOException {
        GetTariffsSpec.assertMapEquals(GetTariffsSpec.getReferenceTariffsMap(), tariffMap);
    }

    @ParameterizedTest
    @EnumSource(TariffType.class)
    void addOrder(TariffType tariffType) {
        CreateOrderSpec.createOrderSuccessful(tariffType, tariffMap, userCounter);
    }
    @Test
    void addOrderNonexistentTariff(){
        ErrorDTO errorDTO = CreateOrderSpec.createOrderFailure();
        assertAll(
                () -> assertEquals(ErrorCode.TARIFF_NOT_FOUND.toString(), errorDTO.getCode()),
                () -> assertEquals("Тариф не найден", errorDTO.getMessage())
        );
    }
}