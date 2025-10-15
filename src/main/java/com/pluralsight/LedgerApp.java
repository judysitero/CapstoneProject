package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LedgerApp {
    static Scanner scanner = new Scanner(System.in);
    static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        // Step 1: Load all data into memory before running the app
        loadTransactions();
        showHomeScreen();
        scanner.close();

    }

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
                Transaction transaction = new Transaction(
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
//BookApp
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

    public static void addDeposit() {
        System.out.println("\n*** Add Deposit Screen Placeholder***");

    }

    public static void makePayment() {
        System.out.println("\n*** Ledger Screen Placeholder ***");

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
}
