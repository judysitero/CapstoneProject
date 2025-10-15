package com.pluralsight;

import com.pluralsight.Capstone.Transaction;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LedgerApp {
    static Scanner scanner = new Scanner(System.in);
    static List<com.pluralsight.Capstone.Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        // Step 1: Load all data into memory before running the app
        loadTransactions();
        showHomeScreen();
        scanner.close();

    }
    // PHASE 2: DATA LOADING

    public static void loadTransactions() {
        String fileName = "transaction.csv";

        try {
            FileReader fileReader = new FileReader("transaction.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine(); //This reads and discards the first line

            String transactionString;

            while ((transactionString = bufferedReader.readLine()) !=null) {
                if (transactionString.trim().isEmpty()) {
                    continue;
                }
                String[] transactionArr = transactionString.split("\\|");

                if (transactionArr.length !=5) {
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
//BookApp  // PHASE 3: MENU METHODS
    public static void showHomeScreen() {
        boolean endPorgram = false;
        while (!endPorgram) {
            System.out.println("\n===== ACCOUNTING LEDGER HOME ====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.println("Please enter your choice: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                case "L":
                    showLedgerScreen();
                case "X":
                    endPorgram = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select D, P, L, or X");
                    break;

            }
        }
        System.out.println("Application exited. Data is safe.");

    }

    //2. Implementing D) Add Deposit
    //This method collects user input, gets the current time, creates a new Transaction object, and saves it. Deposits
    // should have a positive amount.

    public static void addDeposit() {
        System.out.println("\n===== ADD DEPOSIT =====");

        System.out.println("Enter Description: ");
        String description = scanner.nextLine();

        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        // Get current date and time (matching the CSV format)
        // For simplicity, we use String formatting based on the Capstone example: 2023-04-15|10:13:25
        // Note: If you used LocalDate/LocalTime in your Transaction class, you would use those here.

        String date = java.time.LocalDate.now().toString();
        String time = java.time.LocalTime.now().withNano(0).toString();

        Transaction deposit = new Transaction(date, time, description, vendor, amount);

        // Save to the file and add to the in-memory list
        saveTransaction(deposit);
        transactions.add(deposit);

        System.out.println("SUCCESS: Deposit added and saved to ledger.");

    }
    //3. Implementing P) Make Payment (Debit)
    //This method is almost identical to the deposit, but it must ensure the amount is saved as a negative number,
    // as required by the Capstone.

    public static void makePayment() {
        System.out.println("\n===== MAKE PAYMENT =====");

        System.out.println("Enter Description");
        String description = scanner.nextLine();

        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        String date = java.time.LocalDate.now().toString();
        String time = java.time.LocalTime.now().withNano(0).toString();

        double negativeAmount = -1 * amount;

        Transaction payment = new Transaction(date, time, description, vendor, negativeAmount);

        saveTransaction(payment);
        transactions.add(payment);

        System.out.println("SUCCESS: Payment recorded and saved to ledger");

    }

    public static void showLedgerScreen() {
        boolean backToHome = false;
        while (!backToHome) {
            System.out.println("\n==== LEDGER SCREEN =====");
            System.out.println("A) All Entries (Newest First)");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payment Only");
            System.out.println("R) Reports Menu");
            System.out.println("H) Return to Home Screen");
            System.out.println("Please enter your choice: ");

            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "A":
                    displayAllEntries();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case"P":
                    displayPayments();
                    break;
                case "R":
                    showReportsScreen();
                    break;
                case "H":
                    backToHome = true;
                    break;
                default:
                    System.out.println("invalid choice. Please select A, D, P, R, or H.");
                    break;
            }
        }

    }

    public static void displayAllEntries() {
        System.out.println("\n*** Display ALl Entries...");

    }

    public static void displayDeposits() {
        System.out.println("\n**** Display entries...");

    }

    public static void displayPayments() {
        System.out.println("\n**** Display entries...");

    }

    public static void showReportsScreen() {
        System.out.println("\n*** display entries...");

    }
    //phase4:TRANSACTION I/O (The methods you just built)
    //1. The Reusable Save Method: saveTransaction()
    //This method handles the file-writing complexity. Following your instructor's I/O writing style
    // (using BufferedWriter and FileWriter), we must set the FileWriter to append mode to ensure we don't erase the
    // existing transactions. This method will be called after the user successfully enters a new transaction.
    public static void saveTransaction(Transaction transaction) {
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
