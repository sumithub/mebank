package com.mebank.data;

import com.mebank.model.Transaction;
import com.mebank.model.TransactionType;
import com.mebank.util.Utility;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVTransactionsLoader implements TransactionDataProvider {
    private final String filePath;

    public CSVTransactionsLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Transaction> getAllTransactions() throws Exception {
        final String COMMA_DELIMITER = ",";

        List<Transaction> transactions = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String row;
            while ((row = br.readLine()) != null) {
                String[] values = row.split(COMMA_DELIMITER);
                transactions.add(createTransactionByValues(values));
            }
            br.close();
        } catch (IOException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
            throw  fileNotFoundException;
        }
        return transactions;
    }

    private Transaction createTransactionByValues(String... values) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(values[0].trim());
        transaction.setFromAccountId(values[1].trim());
        transaction.setToAccountId(values[2].trim());
        transaction.setDateTime(LocalDateTime.parse(values[3].trim(), Utility.DATE_TIME_FORMATTER));
        transaction.setAmount(new BigDecimal(values[4]));
        TransactionType transactionType = TransactionType.valueOf(values[5]);
        transaction.setTransactionType(transactionType);
        if (transactionType.equals(TransactionType.REVERSAL))
            transaction.setRelatedTransaction(values[6]);
        return transaction;
        }
}
