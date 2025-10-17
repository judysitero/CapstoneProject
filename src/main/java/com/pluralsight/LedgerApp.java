package com.pluralsight;

import com.pluralsight.Transaction;
import com.pluralsight.TransactionManager;
import com.sun.jdi.PathSearchingVirtualMachine;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

/** My User Interface (UI) and Control Center. This tells the data manager what to and displays the result to the user */

public class LedgerApp {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Step 1: Load all data into memory before running the app
        TransactionManager.loadTransactions();
        showHomeScreen();
        scanner.close();

    }
    // PHASE 3: MENU METHODS (use BookApp for Reference)

    /** Main Navigation Loop. Keeps the program running and directs traffic between D, P, L, X based on user input. */
    public static void showHomeScreen() {
        boolean endPorgram = false;

        // The main loop keeps the application running until endProgram is true
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
                    break;
                case "L":
                    showLedgerScreen();
                    break;
                case "X":
                    endPorgram = true;
                    break;
                default:
                    System.out.println("INVALID CHOICE: Please select D, P, L, or X");
                    break;

            }
        }
        System.out.println("Application exited. Data is safe.");

    }

    /**Implementing D) Add Deposit
    This method collects user input, gets the current time, creates a new Transaction object, and saves it.*/

    //The method prompts the user for details (Description, Vendor, Amount).
    public static void addDeposit() {
        System.out.println("\n===== ADD A DEPOSIT =====");

        System.out.println("Enter Description: ");
        String description = scanner.nextLine();

        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        //We will use LocalDate and LocalTime to automatically set the entry time.
        String date = java.time.LocalDate.now().toString();
        String time = java.time.LocalTime.now().withNano(0).toString();

        //We create a new Transaction object
        Transaction deposit = new Transaction(date, time, description, vendor, amount);

        //Call TransactionManager.saveTransaction(deposit) to permanently record the data to the CSV file
        TransactionManager.saveTransaction(deposit);

        //Call TransactionManager.transactions.add(deposit) to update the in-memory list.
        TransactionManager.transactions.add(deposit);


        System.out.println("SUCCESS: Deposit added and saved to ledger.");

    }
    /**Implementing P) Make Payment (Debit). This method is similar to the addDeposit, but
     the amount is saved as a negative number*/

    public static void makePayment() {
        System.out.println("\n===== MAKE A PAYMENT =====");

        System.out.println("Enter Description");
        String description = scanner.nextLine();

        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        String date = java.time.LocalDate.now().toString();
        String time = java.time.LocalTime.now().withNano(0).toString();


        // Multiply by -1 to create a debit entry (ensures amount is negative for Payments)
        double negativeAmount = -1 * amount;

        Transaction payment = new Transaction(date, time, description, vendor, negativeAmount);

        TransactionManager.saveTransaction(payment);
        TransactionManager.transactions.add(payment);

        System.out.println("SUCCESS: Payment recorded and saved to ledger");

    }

    /** This will be out second while-loop but for managing the filters (A, D, P, R, H) and directs control to the display methods.*/

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
                    backToHome = true; // Will exit the Ledger loop and return to the Home screen
                    break;
                default:
                    System.out.println("INVALID CHOICE: Please select A, D, P, R, or H.");
                    break;
            }
        }

    }

    /**MY DISPLAY AND FILTERING METHODS.*/

    //This part here will read and present the entire set of financial transactions to the screen,
    // showing the newest entries first.
    public static void displayAllEntries() {
        System.out.println("\n====== LEDGER: ALL ENTRIES  =====");

        // Printing a header for the table. Use printf.
        System.out.printf("%-10s | %-8s | %-30s | %-20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        // The logic to print 'Newest First' is to loop BACKWARDS through the list.
        // The list index runs from 0 (oldest) up to transactions.size() - 1 (newest).

        // Start index: last element (transactions.size() - 1)
        // Condition: continue as long as index is 0 or greater
        // Update: decrement the index

        for (int i = TransactionManager.transactions.size() - 1; i >= 0; i--) {

            // Get the Transaction object at the current index
            Transaction t = TransactionManager.transactions.get(i);

            // Use printf and Getters for clean, aligned output
            System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");


    }

    // This part here will filter the central data to show only income entries (deposits). Needs to be positive
    public static void displayDeposits() {
        System.out.println("\n===== LEDGER: DEPOSITS ONLY  =====");

        System.out.printf("%-10s | %-8s | %-30s | %-20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------------------------------");


        for (int i = TransactionManager.transactions.size() - 1; i >= 0; i--) {
            Transaction t = TransactionManager.transactions.get(i);

            //Conditional (if statement): Checks if t.getAmount() > 0. Loops: Iterates over the list, but only prints if the condition is met.
            if (t.getAmount() > 0) {
                System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");


    }
    // This also filters the central data to show only expense entries (payments/debits).
    public static void displayPayments() {
        System.out.println("\n===== LEDGER: PAYMENTS ONLY  =====");

        // Print header
        System.out.printf("%-10s | %-8s | %-30s | %-20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for (int i = TransactionManager.transactions.size() - 1; i >= 0; i--) {
            Transaction t = TransactionManager.transactions.get(i);

            //Conditional (if statement): Only show negative amounts
            if (t.getAmount() < 0) {
                System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n",
                        t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount());
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void filterAndDisplayByDate(LocalDate startDate, LocalDate endDate, String reportTitle) {
        System.out.println("\n===== LEDGER: " + reportTitle + " =====");

        System.out.printf("%-10s | %-8s | %-30s | %-20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (int i = TransactionManager.transactions.size() - 1; i >= 0; i--) {
            Transaction t = TransactionManager.transactions.get(i);

            // Get the comparable date
            LocalDate transactionDate = t.getLocalDate();

            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n",
                        t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount());
            }

        }
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    public static void generateMonthToDateReport() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(today.getYear(), today.getMonth(), 1);
        filterAndDisplayByDate(startDate, today, "MONTH TO DATE");

    }

    public static void generatePreviousMonthReport() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);
        LocalDate startDate = LocalDate.of(lastMonth.getYear(), lastMonth.getMonth(),1);
        LocalDate endDate = LocalDate.of(today.getYear(), today.getMonth(), 1).minusDays(1);
        filterAndDisplayByDate(startDate, endDate, "PREVIOUS MONTH" );


    }

    public static void generateYearToDateReport() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(today.getYear(), 1,1);
        filterAndDisplayByDate(startDate, today, "YEAR TO DATE");

    }

    public static void generatePreviousYearReport() {
        LocalDate today = LocalDate.now();
        int lastYear = today.getYear() - 1;
        LocalDate startDate = LocalDate.of(lastYear, 1, 1);
        LocalDate endDate = LocalDate.of(lastYear, 12, 31);
        filterAndDisplayByDate(startDate, endDate, "PREVIOUS YEAR");

    }

    public static void showReportsScreen() {
        String choice;
        do {
            System.out.println("\n===== REPORTS MENU =====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back (to Ledger page)");
            System.out.print("Please enter your choice: ");

            choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "1":
                    generateMonthToDateReport();
                    break;
                case "2":
                    generatePreviousMonthReport();
                    break;
                case "3":
                    generateYearToDateReport();
                    break;
                case "4":
                    generatePreviousYearReport();
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

    // This part here will filter the central data based on user input.
public static void findEntriesByVendor() {
    System.out.println("\n===== REPORT: SEARCH BY VENDOR =====");
    System.out.println("Enter Vendor Name to search for: ");
    String searchVendor = scanner.nextLine().trim();

    // The logic to display results is the same as the "All Entries" display
    System.out.printf("%-10s | %-8s | %-30s | %20s | %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
    System.out.println("-------------------------------------------------------------------------------------------");

    for (int i = TransactionManager.transactions.size() -1; i >= 0; i--) {
        Transaction t = TransactionManager.transactions.get(i);

        //We will use t.getVendor().trim().equalsIgnoreCase(searchVendor) to find case-insensitive matches.
        // Loops: Iterates through all entries and prints only the matching ones
        if (t.getVendor().trim().equalsIgnoreCase(searchVendor)) {
            System.out.printf("%-10s | %-8s | %-30s | %-20s | $%,10.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }
    System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

}


}
