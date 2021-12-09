package com.mebank.data;

import com.mebank.model.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class CSVTransactionsLoaderTest {

    @Test
    @DisplayName("CSV transaction loader reads all transaction records")
    void CSVTransactionLoaderTest() throws Exception {
    CSVTransactionsLoader csvTransactionsLoader = new CSVTransactionsLoader("src/main/resources/transactions.csv");
    List<Transaction> transactionList = csvTransactionsLoader.getAllTransactions();
    assertEquals(5, transactionList.size());
    }

    @Test
    @DisplayName("Invalid filename throws exception")
    void invalidFilePathThrowsExceptionTest() {
        Throwable exception =assertThrows(FileNotFoundException.class, () -> new CSVTransactionsLoader("doesNotExist.csv").getAllTransactions());
        String expected = "No such file or directory";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }
}