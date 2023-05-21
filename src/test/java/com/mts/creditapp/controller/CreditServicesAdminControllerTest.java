package com.mts.creditapp.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import spec.CreateTariffSpec;
import utils.TableUpdater;

import java.io.FileNotFoundException;
@Execution(ExecutionMode.CONCURRENT)
public class CreditServicesAdminControllerTest {
    @AfterAll
    public static void cleanTable() throws FileNotFoundException {
        TableUpdater.truncateTable("loan_order");
        TableUpdater.truncateTable("tariff");
        TableUpdater.initialiseTariffs("tariff");
    }
    @Test
    void addTariffSuccessful() {
        CreateTariffSpec.createTariff("MORTGAGE", "5.9%");
    }
    @Test
    void addTariffWithNegativeInterestRate() {
        CreateTariffSpec.createTariffWithNegativeInterestRate("SUPER CREDIT", Integer.MIN_VALUE+"%");
    }
    @Test
    void addTariffWithoutTypeFailure() {
        CreateTariffSpec.createTariffWithoutType( "15%");
    }
    @Test
    void addTariffWithoutInterestRateFailure() {
        CreateTariffSpec.createTariffWithoutInterestRate("NEW TARIFF" );
    }
    @Test
    void addExistingTariff() {
        CreateTariffSpec.createExistingTariff("BUSINESS", "2%");
    }

}