package com.mebank.domain;

import com.mebank.model.AccountBalance;
import com.mebank.model.Transaction;
import com.mebank.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RelativeAccountBalanceCalculator {
    private final List<Transaction> transactionList;

    public RelativeAccountBalanceCalculator(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public AccountBalance calculate(String accountId, LocalDateTime fromDate, LocalDateTime toDate) {
        List<Transaction> accountFilteredTransactions = filterByAccountId(accountId, transactionList);
        List<String> reversalTransactionIds =  filterByTransactionType(accountFilteredTransactions, TransactionType.REVERSAL);
        List<Transaction> filteredPaymentTransactions = removeReversalTransactions(accountFilteredTransactions, reversalTransactionIds);
        List<Transaction> filteredDateTransactions = filterTransactionsByDate(filteredPaymentTransactions, fromDate, toDate);
        return calculateRelativeBalance(accountId, filteredDateTransactions);
    }

    private List<Transaction> filterByAccountId(String accountId, List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getFromAccountId().equals(accountId))
                .collect(Collectors.toList());
    }

    private List<String> filterByTransactionType(List<Transaction> transactions, TransactionType transactionType) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransactionType().equals(transactionType))
                .map(Transaction::getRelatedTransaction)
                .collect(Collectors.toList());
    }

    private List<Transaction> removeReversalTransactions(List<Transaction> transactions, List<String> reversalTransactions) {
        List<Transaction> filteredPaymentTransactions = new ArrayList<>();
        for (Transaction transaction: transactions) {
            if (reversalTransactions.contains(transaction.getTransactionId()) || transaction.getTransactionType().equals(TransactionType.REVERSAL))
                continue;
            filteredPaymentTransactions.add(transaction);
        }
        return filteredPaymentTransactions;
    }

    private List<Transaction> filterTransactionsByDate(List<Transaction> filteredPaymentTransactions, LocalDateTime fromDate, LocalDateTime toDate) {
        return filteredPaymentTransactions.stream().
                filter(transaction -> (transaction.getDateTime().compareTo(fromDate) >= 0)
                        && (transaction.getDateTime().compareTo(toDate) <= 0))
                .collect(Collectors.toList());
    }

    private AccountBalance calculateRelativeBalance(String accountId, List<Transaction> filteredDateTransactions) {
        BigDecimal amount = new BigDecimal("0.0");
        for (Transaction t: filteredDateTransactions)
            amount = amount.subtract(t.getAmount());
        return new AccountBalance(accountId, amount, filteredDateTransactions.size());
    }
}
