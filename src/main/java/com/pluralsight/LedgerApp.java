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

        // --- CRITICAL LOGIC: Ensure amount is negative for Payments ---
        // Multiply by -1 to create a debit entry
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
                case "P":
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
        System.out.println("\n====== LEDGER: ALL ENTRIES (NEWEST FIRST) =====");

        // Print a header for the table
        System.out.printf("%-10s | %-8s | %-30s | %-20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        // The logic to print 'Newest First' is to loop BACKWARDS through the list.
        // The list index runs from 0 (oldest) up to transactions.size() - 1 (newest).

        // Start index: last element (transactions.size() - 1)
        // Condition: continue as long as index is 0 or greater
        // Update: decrement the index

        for (int i = transactions.size() - 1; i >= 0; i--) {

            // Get the Transaction object at the current inde
            Transaction t = transactions.get(i);

            // Use printf and Getters for clean, aligned output
            System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");


    }

    public static void displayDeposits() {
        System.out.println("\n===== LEDGER: DEPOSITS ONLY (NEWEST FIRST) =====");


        System.out.printf("%-10s | %-8s | %-30s | %-20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------------------------------");


        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);

            if (t.getAmount() > 0) {
                System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");


    }
    public static void displayPayments() {
        System.out.println("\n===== LEDGER: PAYMENTS ONLY (NEWEST FIRST) =====");

        // Print header
        System.out.printf("%-10s | %-8s | %-30s | %-20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);

            if (t.getAmount() < 0) {
                // Use Math.abs() to display the amount as positive for reporting clarity
                System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n",
                        t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        Math.abs(t.getAmount()));
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
    } // <--- THIS is the required closing brace!
    public static void showReportsScreen() {
        String choice;
        do {
            System.out.println("----");
            System.out.println("----");

            choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "1":
                case "2":
                case "3":
                case "4":
                    System.out.println("Report " + choice + " is a Placeholder for Date Logic");
                    break;
                case "5":
                    findEntriesByVendor();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("invalid choice. Please select 1-5, or 0 to go back ");
                    break;
            }

        } while(!choice.equals("0"));
    }


public static void findEntriesByVendor() {
    System.out.println("\n===== REPORT: SEARCH BY VENDOR =====");
    System.out.println("Enter Vendor Name to search for: ");
    String searchVendor = scanner.nextLine().trim();

    // The logic to display results is the same as the "All Entries" display
    System.out.printf("%-10s | %-8s | %-30s | %20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
    System.out.println("-------------------------------------------------------------------------------------------");

    for (int i = transactions.size() -1; i >= 0; i--) {
        Transaction t = transactions.get(i);

        if (t.getVendor().trim().equalsIgnoreCase(searchVendor)) {
            System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }
    System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

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
