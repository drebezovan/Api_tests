package com.mts.creditapp.controller;

import com.mts.creditapp.entity.inputEntities.NewTariffInput;
import com.mts.creditapp.service.CreditAdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/loan-service-admin")
public class CreditServicesAdminController {
    private final CreditAdminServices creditAdminServices;
    @PostMapping("/addTariff")
    public ResponseEntity<Integer> addTariff(@RequestBody NewTariffInput newTariffInput) {
        return ResponseEntity.ok(creditAdminServices.newTariff(newTariffInput));
    }
}
