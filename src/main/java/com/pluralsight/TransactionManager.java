package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    static List<com.pluralsight.Capstone.Transaction> transactions = new ArrayList<>();

    public static void loadTransactions() {
        //Data Loading
        String fileName = "transaction.csv";

        try {
            FileReader fileReader = new FileReader("transaction.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine(); //This reads and discards the first line

            String transactionString;

            while ((transactionString = bufferedReader.readLine()) != null) {
                if (transactionString.trim().isEmpty()) {
                    continue;
                }
                String[] transactionArr = transactionString.split("\\|");

                if (transactionArr.length != 5) {
                    System.out.println("Skip malformed line: " + transactionString);
                    continue;
                }
                com.pluralsight.Capstone.Transaction transaction = new com.pluralsight.Capstone.Transaction(
                        transactionArr[0].trim(),
                        transactionArr[1].trim(),
                        transactionArr[2].trim(),
                        transactionArr[3].trim(),
                        Double.parseDouble(transactionArr[4].trim())
                );
                transactions.add(transaction);
            }
            bufferedReader.close();

        } catch (IOException e) {

            System.out.println("Error reading transaction file: " + e.getMessage());

            throw new RuntimeException(e);
        }

    }
    //phase4:TRANSACTION I/O (The methods you just built)
    //1. The Reusable Save Method: saveTransaction()
    //This method handles the file-writing complexity. Following your instructor's I/O writing style
    // (using BufferedWriter and FileWriter), we must set the FileWriter to append mode to ensure we don't erase the
    // existing transactions. This method will be called after the user successfully enters a new transaction.
    public static void saveTransaction(com.pluralsight.Capstone.Transaction transaction) {
        String fileName = "transaction.csv";
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(transaction.toCsvString() + "\n");

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    }


