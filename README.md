# Accounting Ledger Application

A comprehensive Java-based accounting ledger system that allows users to track financial transactions, manage deposits and payments, and generate detailed financial reports.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Usage Guide](#usage-guide)
- [Output Descriptions](#output-descriptions)
- [Technical Details](#technical-details)
- [Future Enhancements](#future-enhancements)

## Overview

The Accounting Ledger Application is a console-based financial tracking system that helps users manage their income and expenses. It provides a user-friendly interface for recording transactions, viewing financial history, and generating custom reports.

## ✨ Features

- **💰 Deposit Management**: Record income transactions with automatic timestamping
- **💳 Payment Tracking**: Log expense transactions with negative amount handling
- **📊 Ledger Display**: View all transactions with multiple filtering options
- **🔍 Advanced Reporting**: Generate custom reports by date range and vendor
- **💾 Data Persistence**: Automatic CSV file storage and loading
- **🕒 Real-time Timestamps**: Automatic date and time recording for all transactions

## 🏗️ Project Structure

```
src/
├── LedgerApp.java          # Main application class with UI and menu navigation
├── Transaction.java        # Transaction data model with getters/setters
└── TransactionManager.java # Data persistence and transaction management
```


## 📝 Usage Guide

### Main Menu Options
- **D) Add Deposit**: Record new income transactions
- **P) Make Payment**: Record new expense transactions  
- **L) Ledger**: Access transaction history and reporting
- **X) Exit**: Safely close the application

### Ledger Screen Options
- **A) All Entries**: Complete transaction history (newest first)
- **D) Deposits Only**: Filtered view of income transactions
- **P) Payments Only**: Filtered view of expense transactions
- **R) Reports Menu**: Advanced filtering and search options
- **H) Home**: Return to main menu

### Reports Menu Options
- **1) Month To Date**: Current month transactions (placeholder)
- **2) Previous Month**: Last month's transactions (placeholder) 
- **3) Year To Date**: Current year transactions (placeholder)
- **4) Previous Year**: Last year's transactions (placeholder)
- **5) Search by Vendor**: Find transactions by vendor name
- **0) Back**: Return to ledger screen

## 🖥️ Output Descriptions

### 🏠 Home Screen Output
<img width="324" height="159" alt="image" src="https://github.com/user-attachments/assets/408338e8-c251-4988-8f8f-5c4e865fbec0" />

**Purpose**: Main navigation hub with clear menu options and professional formatting for easy user interaction.

### 💰 Deposit Entry Output
<img width="750" height="191" alt="image" src="https://github.com/user-attachments/assets/4dac10d0-b819-4498-88ee-016b3b335535" />

**Purpose**: Clean, focused interface for recording income transactions with immediate success confirmation.

### 💳 Payment Entry Output  

```

===== MAKE A PAYMENT =====
Enter Description: 
Enter Vendor: 
Enter Amount: 
SUCCESS: Payment recorded and saved to ledger
```
**Purpose**: Dedicated payment interface that automatically converts amounts to negative values for proper accounting.

### 📋 Ledger Display Output
```
====== LEDGER: ALL ENTRIES =====
Date       | Time     | Description                    | Vendor               | Amount    
--------------------------------------------------------------------------------------------
2023-10-15 | 14:30:25 | Grocery Shopping              | SuperMart            | $    45.67
2023-10-14 | 09:15:10 | Paycheck Deposit              | ABC Company          | $ 1,500.00
```
**Purpose**: Professional tabular display with proper column alignment, currency formatting, and newest-first sorting for easy financial review.

### 💵 Deposits-Only Output
```
===== LEDGER: DEPOSITS ONLY =====
Date       | Time     | Description                    | Vendor               | Amount    
-------------------------------------------------------------------------------------------------
2023-10-14 | 09:15:10 | Paycheck Deposit              | ABC Company          | $ 1,500.00
2023-10-01 | 08:45:00 | Freelance Work                | Client XYZ           | $   750.00
```
**Purpose**: Filtered view showing only positive transactions with the same professional formatting as the full ledger.

### 🛒 Payments-Only Output
```
===== LEDGER: PAYMENTS ONLY =====
Date       | Time     | Description                    | Vendor               | Amount    
-----------------------------------------------------------------------------------------------------------------------------
2023-10-15 | 14:30:25 | Grocery Shopping              | SuperMart            | $    45.67
2023-10-12 | 19:20:15 | Dinner with Friends           | Local Restaurant     | $    85.43
```
**Purpose**: Expense-focused view displaying absolute values of negative amounts for clearer financial analysis.

### 🔍 Vendor Search Output
```
===== REPORT: SEARCH BY VENDOR =====
Enter Vendor Name to search for: SuperMart

Date       | Time     | Description                    | Vendor               | Amount    
-------------------------------------------------------------------------------------------
2023-10-15 | 14:30:25 | Grocery Shopping              | SuperMart            | $    45.67
2023-10-08 | 16:45:30 | Weekly Groceries              | SuperMart            | $    62.15
```
**Purpose**: Case-insensitive vendor search with full transaction details, helping users track spending with specific merchants.

### 📊 Reports Menu Output
```
===== REPORTS MENU =====
1) Month To Date
2) Previous Month  
3) Year To Date
4) Previous Year
5) Search by Vendor
0) Back (to Ledger page)
```
**Purpose**: Extended reporting interface with placeholder options for future date-based filtering functionality.

## 🔧 Technical Details

### Data Storage
- **Format**: Pipe-delimited CSV file (`transactions.csv`)
- **Structure**: `date|time|description|vendor|amount`
- **Persistence**: Automatic loading on startup and saving after each transaction

### Key Algorithms
- **Reverse Iteration**: Displays newest transactions first
- **Real-time Filtering**: Dynamic filtering of deposits and payments
- **Case-insensitive Search**: Vendor lookup without case sensitivity
- **Amount Processing**: Automatic negative conversion for payments

### Error Handling
- File I/O exceptions with user-friendly messages
- Input validation for malformed data
- Graceful application termination

## 🔮 Future Enhancements

- [ ] Date-range filtering for reports
- [ ] Monthly and yearly summary statistics
- [ ] Transaction editing and deletion
- [ ] Category-based transaction organization
- [ ] Export functionality for PDF/Excel reports
- [ ] Graphical data visualization

---

**Note**: This application demonstrates object-oriented programming principles, file I/O operations, and user interface design while providing practical financial management capabilities.
