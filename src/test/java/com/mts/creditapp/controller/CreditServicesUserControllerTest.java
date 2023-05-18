package com.mts.creditapp.controller;

import dto.GetTariffsResponse;
import org.junit.jupiter.api.Test;
import spec.GetTariffsSpec;

import java.io.IOException;

class CreditServicesUserControllerTest {

    @Test
    void getTariffs() throws IOException {

        System.out.println(GetTariffsSpec.getTariffs().toString());
        GetTariffsSpec.getYaml();
//        GetTariffsResponse tariffDTO = GetTariffsSpec.getTariffs();
//        GetTariffsSpec.assertGetTariff(tariffDTO);
    }

    @Test
    void addOrder() {
    }

    @Test
    void getStatusOrder() {
    }

    @Test
    void deleteOrder() {
    }
}