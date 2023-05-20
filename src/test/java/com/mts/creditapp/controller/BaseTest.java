package com.mts.creditapp.controller;

import com.mts.creditapp.repository.LoanOrderRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    @Autowired
     LoanOrderRep loanOrderRep;
    @AfterEach
    void cleanDataBaseAfterTest() {
        loanOrderRep.deleteAllOrders();
    }
    @BeforeAll
    void  cleanDataBAseBeforeStart() {
        loanOrderRep.deleteAllOrders();
    }
}