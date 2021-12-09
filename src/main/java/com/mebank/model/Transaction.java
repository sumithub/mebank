package com.mebank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String RelatedTransaction;

    public Transaction(String transactionId, String fromAccountId, String toAccountId, LocalDateTime dateTime, BigDecimal amount, TransactionType transactionType, String relatedTransaction) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.dateTime = dateTime;
        this.amount = amount;
        this.transactionType = transactionType;
        RelatedTransaction = relatedTransaction;
    }

    public Transaction() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getRelatedTransaction() {
        return RelatedTransaction;
    }

    public void setRelatedTransaction(String relatedTransaction) {
        RelatedTransaction = relatedTransaction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", fromAccountId='" + fromAccountId + '\'' +
                ", toAccountId='" + toAccountId + '\'' +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", RelatedTransaction='" + RelatedTransaction + '\'' +
                '}';
    }
}
