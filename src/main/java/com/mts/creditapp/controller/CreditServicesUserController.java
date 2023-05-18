package com.mts.creditapp.controller;

import com.mts.creditapp.entity.inputEntities.DeleteCreditInput;
import com.mts.creditapp.entity.inputEntities.NewCreditInput;
import com.mts.creditapp.entity.outputEnitites.outputs.GenericOutput;
import com.mts.creditapp.service.CreditUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loan-service")
public class CreditServicesUserController {
    private final CreditUserServices creditUserServices;
    @GetMapping("/getTariffs")
    public ResponseEntity<GenericOutput> getTariffs() {
        return ResponseEntity.ok(creditUserServices.getAllTariffs());
    }
    @PostMapping("/order")
    public ResponseEntity<GenericOutput> addOrder(@RequestBody NewCreditInput order) {
        return ResponseEntity.ok(creditUserServices.newCredit(order));
    }
    @GetMapping("/getStatusOrder")
    public ResponseEntity<GenericOutput> getStatusOrder(@RequestParam UUID orderId) {
        return ResponseEntity.ok(creditUserServices.getOrderStatus(orderId));
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<Integer> deleteOrder(@RequestBody DeleteCreditInput deleteCreditInput) {
        return ResponseEntity.ok(creditUserServices.deleteOrder(deleteCreditInput));
    }

}
