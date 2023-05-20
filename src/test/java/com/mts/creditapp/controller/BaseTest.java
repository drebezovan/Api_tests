package com.mts.creditapp.controller;

import com.mts.creditapp.repository.LoanOrderRep;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {
    @Autowired
    LoanOrderRep loanOrderRep;

    @AfterEach
    void deleteLoanOrders() {
        loanOrderRep.deleteAllOrders();
    }
}