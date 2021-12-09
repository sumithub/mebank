package com.mebank.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountBalanceTest {

    @Test
    void getCurrencyAmountTest() {
        AccountBalance accountBalance = new AccountBalance("1", new BigDecimal("15.35"), 2);
        assertEquals("$15.35", accountBalance.getCurrencyAmount());
    }
}