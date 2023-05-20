package com.mts.creditapp.controller;

import com.mts.creditapp.entity.tableEntities.Tariff;
import constants.ErrorCode;
import constants.TariffType;
import dto.ErrorDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import spec.CreateOrderSpec;
import spec.GetTariffsSpec;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CreditServicesUserControllerTest extends BaseTest{

    public static Map<String, Tariff> tariffMap;

    @BeforeAll
    static void getTariffsTypes() {
        tariffMap = GetTariffsSpec.getTariffsMap();
    }

    @Test
    void getTariffs() throws IOException {
        GetTariffsSpec.assertMapEquals(GetTariffsSpec.getReferenceTariffsMap(), tariffMap);
    }

    @ParameterizedTest
    @EnumSource(TariffType.class)
    void addOrder(TariffType tariffType) {
        CreateOrderSpec.createOrderSuccessful(tariffType, tariffMap, 1L);
    }

    @Test
    void addAlreadyExistingOrder() {
        CreateOrderSpec.createOrderSuccessful(TariffType.CONSUMER, tariffMap, 1L);
        ErrorDTO errorDTO = CreateOrderSpec.createOrderError(TariffType.CONSUMER, tariffMap, 1L);
        assertAll(
                () -> assertEquals(ErrorCode.LOAN_CONSIDERATION.toString(), errorDTO.getCode()),
                () -> assertEquals("Заявка рассматривается", errorDTO.getMessage())
        );
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