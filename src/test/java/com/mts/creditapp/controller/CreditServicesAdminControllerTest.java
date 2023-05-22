package com.mts.creditapp.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import spec.CreateTariffSpec;
import utils.TableUpdater;

import java.io.FileNotFoundException;

public class CreditServicesAdminControllerTest {
    @AfterAll
    public static void cleanTable() throws FileNotFoundException {
        TableUpdater.truncateTable("loan_order");
        TableUpdater.truncateTable("tariff");
        TableUpdater.initialiseTariffs();
    }

    @Tags(value = {@Tag("Smoke"), @Tag("Regress")})
    @Test
    void addTariffSuccessful() {
        CreateTariffSpec.createTariffSuccessful("MORTGAGE", "5.9%");
    }

    @Tag("Regress")
    @ParameterizedTest
    @CsvSource({
            "SUPER CREDIT, -2000000%",
            "            , 15%      ",
            "NEW TARIFF  ,          ",
            "BUSINESS    , 2%       "})
    void addWrongTariffs(String type, String interestRate) {
        CreateTariffSpec.createTariffError(type, interestRate);
    }

}