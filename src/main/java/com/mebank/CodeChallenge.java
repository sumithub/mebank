package com.mebank;

import com.mebank.data.CSVTransactionsLoader;
import com.mebank.data.TransactionDataProvider;
import com.mebank.domain.RelativeAccountBalanceCalculator;
import com.mebank.model.AccountBalance;
import com.mebank.model.Transaction;
import com.mebank.util.Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CodeChallenge {
    public static final String FILE_PATH = "src/main/resources/transactions.csv";
    public static void main(String[] args) {

        // initialize data loader
        TransactionDataProvider csvDataProvider = new CSVTransactionsLoader(FILE_PATH);
        // get transactions
        List<Transaction> transactionRecords = null;
        try {
            transactionRecords = csvDataProvider.getAllTransactions();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        // initialize relative account balance calculator
        RelativeAccountBalanceCalculator relativeAccountBalance = new RelativeAccountBalanceCalculator(transactionRecords);

        // take user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter accountId: ");
        String accountId = scanner.nextLine();
        System.out.println("Enter fromDate: ");
        String fromDate = scanner.nextLine();
        System.out.println("Enter toDate: ");
        String toDate = scanner.nextLine();

        // calculate relative account balance

        if (accountId != null && fromDate != null && toDate != null) {
            try {
                AccountBalance accountBalance = relativeAccountBalance.calculate(accountId,
                        LocalDateTime.parse(fromDate, Utility.DATE_TIME_FORMATTER),
                        LocalDateTime.parse(toDate, Utility.DATE_TIME_FORMATTER));
                // display account balance
                System.out.println("Relative Balance for the period is: " + accountBalance.getCurrencyAmount());
                System.out.println("Number of transactions included is: " + accountBalance.getTransactionCount());
            }
            catch (DateTimeParseException dtp) {
                System.out.println("Date Time Parse Error: " +dtp.getMessage());
                System.out.println("Accepted date format ex: 20/10/2018 12:47:55");
            }
        }
    }
}
