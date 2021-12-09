package com.mebank.data;

import com.mebank.model.Transaction;

import java.util.List;

public interface TransactionDataProvider {
    List<Transaction> getAllTransactions() throws Exception;
}
