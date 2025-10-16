package com.pluralsight;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
   public static List<Transaction> transactions = new ArrayList<>();

    /**
     * This will read all transactions from the CSV file and populate the static list.
     * This method is called once when the application starts.
     */

    public static void loadTransactions() {
        //Data Loading
        String fileName = "transaction.csv";

        // Using try-catch for Exception Handling (Workbook 3)
        try {
            // Setting up I/O pipeline
            FileReader fileReader = new FileReader("transaction.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine(); //This reads and discards the first line

            String transactionString;

            // Loop and Parse: Read lines until the end of the file (null)
            while ((transactionString = bufferedReader.readLine()) != null) {

                if (transactionString.trim().isEmpty()) {
                    continue;
                }

                // Split the line into individual parts using the pipe '|' delimiter
                String[] transactionArr = transactionString.split("\\|");

                // Basic validation: ensure we have exactly 5 parts
                if (transactionArr.length != 5) {
                    System.out.println("Skip malformed line: " + transactionString);
                    continue;
                }
                // Convert data types and create the Transaction object (OOP)
                com.pluralsight.Transaction transaction = new com.pluralsight.Transaction(
                        transactionArr[0].trim(),
                        transactionArr[1].trim(),
                        transactionArr[2].trim(),
                        transactionArr[3].trim(),
                        Double.parseDouble(transactionArr[4].trim())
                );

                // Add the new object to the list in memory
                transactions.add(transaction);
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("Error reading transaction file: " + e.getMessage());

            // Using RuntimeException to halt gracefully
            throw new RuntimeException(e);
        }

    }
    /**Phase 4:The Reusable Save Method: saveTransaction()
     This method handles the file-writing complexity(using BufferedWriter and FileWriter). We must set the FileWriter
     to append mode to ensure we don't erase the existing transactions. This method will be called after the user
     successfully enters a new transaction.*/
    public static void saveTransaction(com.pluralsight.Transaction transaction) {
        String fileName = "transaction.csv";
        try {
            // FileWriter(fileName, true) sets the file to APPEND mode (Workbook 3 style)
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Using the Transaction object's helper method to format the data
            bufferedWriter.write(transaction.toCsvString() + "\n");

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}


