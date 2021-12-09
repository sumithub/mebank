package com.mebank.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class AccountBalance {
    private String accountId;
    private int transactionCount;
    private BigDecimal amount;

    public AccountBalance(String accountId, BigDecimal amount, int transactionCount) {
        this.accountId = accountId;
        this.amount = amount;
        this.transactionCount = transactionCount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public String getCurrencyAmount() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(this.amount);
    }
}
