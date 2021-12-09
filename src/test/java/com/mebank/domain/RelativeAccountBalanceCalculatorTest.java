package com.mebank.domain;

import com.mebank.model.AccountBalance;
import com.mebank.model.Transaction;
import com.mebank.model.TransactionType;
import com.mebank.util.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RelativeAccountBalanceCalculatorTest {

   static List<Transaction> transactionList = new ArrayList<>();

    @BeforeAll
    static void init() {
        // given
        transactionList.add(new Transaction
                ("TX1", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 12:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("25"),
                        TransactionType.PAYMENT, null));
        transactionList.add(new Transaction
                ("TX2", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 15:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("5"),
                        TransactionType.PAYMENT, null));
        transactionList.add(new Transaction
                ("TX3", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 18:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("5"),
                        TransactionType.REVERSAL, "TX2"));
        transactionList.add(new Transaction
                ("TX4", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 20:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("10"),
                        TransactionType.PAYMENT, null));
        transactionList.add(new Transaction
                ("TX5", "AC5", "AC5",
                        LocalDateTime.parse("20/10/2018 10:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("77"),
                        TransactionType.PAYMENT, null));
    }

    @InjectMocks
    private RelativeAccountBalanceCalculator relativeAccountBalanceCalculator = new RelativeAccountBalanceCalculator(transactionList);

    @Test
    void filterByAccountIdTest() throws Exception {
        // when
        List<Transaction> filteredTransactions = Whitebox.invokeMethod(relativeAccountBalanceCalculator, "filterByAccountId", "AC5", transactionList);
        // then
        assertEquals(1, filteredTransactions.size());
        assertEquals("AC5", filteredTransactions.get(0).getFromAccountId());
    }

    @Test
    void filterByTransactionTypeTest() throws Exception{
        // when
        List<Transaction> filteredTransactions = Whitebox.invokeMethod(relativeAccountBalanceCalculator, "filterByTransactionType", transactionList, TransactionType.PAYMENT);
       // then
        assertEquals(4, filteredTransactions.size());
    }

    @Test
    void removeReversalTransactionsTest() throws Exception {
        // given
       Transaction reversalTransaction =  new Transaction
                ("TX3", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 18:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("5"),
                        TransactionType.REVERSAL, "TX2");

        List<Transaction> reversalTransactionList = new ArrayList<>();
        reversalTransactionList.add(reversalTransaction) ;
        // when
        List<Transaction> filteredTransactions = Whitebox.invokeMethod(relativeAccountBalanceCalculator, "removeReversalTransactions",
                transactionList, reversalTransactionList);
        // then
        assertEquals(4, filteredTransactions.size());
        for (Transaction t: filteredTransactions)
            assertNotEquals(t.getTransactionId(), reversalTransaction.getTransactionId());
    }

    @Test
    void filterTransactionsByDateTest() throws Exception {
        // given
        LocalDateTime fromDate = LocalDateTime.parse("20/10/2018 12:47:55", Utility.DATE_TIME_FORMATTER);
        LocalDateTime toDate = LocalDateTime.parse("20/10/2018 19:47:55", Utility.DATE_TIME_FORMATTER);
        // when
        List<Transaction> dateFilteredTransactions = Whitebox.invokeMethod(relativeAccountBalanceCalculator, "filterTransactionsByDate",
                transactionList, fromDate, toDate);
        // then
        assertEquals(3, dateFilteredTransactions.size());
    }

    @Test
    void calculateRelativeBalanceTest() throws Exception {
        // given
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction
                ("TX1", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 12:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("25"),
                        TransactionType.PAYMENT, null));
        transactions.add(new Transaction
                ("TX2", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 15:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("5"),
                        TransactionType.PAYMENT, null));
        transactions.add(new Transaction
                ("TX3", "AC1", "AC5",
                        LocalDateTime.parse("20/10/2018 20:47:55", Utility.DATE_TIME_FORMATTER), new BigDecimal("10"),
                        TransactionType.PAYMENT, null));
        // when
        AccountBalance accountBalance = Whitebox.invokeMethod(relativeAccountBalanceCalculator, "calculateRelativeBalance",
                 "AC1", transactions);
        // then
        assertNotNull(accountBalance);
        assertEquals("-$40.00", accountBalance.getCurrencyAmount());
    }
}