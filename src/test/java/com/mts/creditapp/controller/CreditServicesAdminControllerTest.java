package com.mts.creditapp.controller;

import org.junit.jupiter.api.Test;
import spec.CreateTariffSpec;

public class CreditServicesAdminControllerTest {
    @Test
    void addTariffSuccessful() {
        CreateTariffSpec.createTariff("MORTGAGE", "5.9%");
    }
    @Test
    void addTariffWithoutTypeFailure() {
        CreateTariffSpec.createTariffWithoutType( "15%");
    }
    @Test
    void addTariffWithoutInterestRateFailure() {
        CreateTariffSpec.createTariffWithoutInterestRate("NEW TARIFF" );
    }
}
