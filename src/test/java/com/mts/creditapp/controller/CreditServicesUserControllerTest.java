package com.mts.creditapp.controller;

import constants.ErrorCode;
import constants.TariffType;
import dto.ErrorDTO;
import dto.TariffDTO;
import org.junit.jupiter.api.*;
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

class CreditServicesUserControllerTest {

    public static Map<String, TariffDTO> tariffMap;
    static int userCounter = 101;

    @BeforeAll
    static void getTariffsTypesAndCreateUser() {
        tariffMap = GetTariffsSpec.getTariffsMap();
    }

    @AfterAll
    public static void cleanTable() throws FileNotFoundException {
        TableUpdater.truncateTable("loan_order");
    }

    @AfterEach
    public void updateCounter() {
        userCounter++;
    }

    @Tag("Regress")
    @Test
    void addAlreadyExistingOrder() {
        CreateOrderSpec.createOrderSuccessful(TariffType.BUSINESS, tariffMap, 100);
        ErrorDTO errorDTO = CreateOrderSpec.createOrderError(TariffType.BUSINESS, tariffMap, 100);
        assertAll(
                () -> assertEquals(ErrorCode.LOAN_CONSIDERATION.toString(), errorDTO.getCode()),
                () -> assertEquals("Заявка рассматривается", errorDTO.getMessage())
        );
    }

    @Tags(value = {@Tag("Smoke"), @Tag("Regress")})
    @Test
    void getTariffs() throws IOException {
        GetTariffsSpec.assertMapEquals(GetTariffsSpec.getReferenceTariffsMap(), tariffMap);
    }

    @Tags(value = {@Tag("Smoke"), @Tag("Regress")})
    @Test
    void addOrderSmoke() {
        CreateOrderSpec.createOrderSuccessful(TariffType.RETIRED, tariffMap, userCounter);
    }

    @Tag("Regress")
    @ParameterizedTest
    @EnumSource(names = {"CONSUMER", "BUSINESS"})
    void addOrder(TariffType tariffType) {
        CreateOrderSpec.createOrderSuccessful(tariffType, tariffMap, userCounter);
    }

    @Tag("Regress")
    @Test
    void addOrderNonexistentTariff() {
        ErrorDTO errorDTO = CreateOrderSpec.createOrderFailure();
        assertAll(
                () -> assertEquals(ErrorCode.TARIFF_NOT_FOUND.toString(), errorDTO.getCode()),
                () -> assertEquals("Тариф не найден", errorDTO.getMessage())
        );
    }
}