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

## Features

- **💰 Deposit Management**: Record income transactions with automatic timestamping
- **💳 Payment Tracking**: Log expense transactions with negative amount handling
- **📊 Ledger Display**: View all transactions with multiple filtering options
- **📅 Advanced Date Filtering: Month-to-date, previous month, year-to-date, and previous year reports
- **🔍 Vendor Search: Find transactions by vendor name with case-insensitive matching  
- **💾 Data Persistence**: Automatic CSV file storage and loading
- **🕒 Real-time Timestamps**: Automatic date and time recording for all transactions

## Project Structure

```
src/
├── LedgerApp.java          # Main application class with UI and menu navigation
├── Transaction.java        # Transaction data model with getters/setters
└── TransactionManager.java # Data persistence and transaction management
```


## Usage Guide

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

## Output Descriptions

### 🏠 Home Screen Output
<img width="820" height="145" alt="image" src="https://github.com/user-attachments/assets/2229cfa4-b1c2-45af-8d27-e069dadcad89" />


**Purpose**: Main navigation hub with clear menu options and professional formatting for easy user interaction.

### 💰 Deposit Entry Output
<img width="824" height="186" alt="image" src="https://github.com/user-attachments/assets/fb1efbf5-89e8-4fb9-b65e-08874b6a7c50" />


**Purpose**: Clean, focused interface for recording income transactions with immediate success confirmation.

### 💳 Payment Entry Output  
<img width="827" height="194" alt="image" src="https://github.com/user-attachments/assets/fdb507b3-edde-4689-b685-3195c2d895a5" />

**Purpose**: Dedicated payment interface that automatically converts amounts to negative values for proper accounting.

### 📋 Ledger Display Output
<img width="820" height="344" alt="image" src="https://github.com/user-attachments/assets/bd75bd09-cba9-40fe-9c7d-808fabb5ac8e" />

**Purpose**: Professional tabular display with proper column alignment, currency formatting, and newest-first sorting for easy financial review.

### 💵 Deposits-Only Output
<img width="839" height="217" alt="image" src="https://github.com/user-attachments/assets/6a01dd4c-880c-4402-9fb7-7b5360fc6620" />

**Purpose**: Filtered view showing only positive transactions with the same professional formatting as the full ledger.

### 🛒 Payments-Only Output
<img width="832" height="212" alt="image" src="https://github.com/user-attachments/assets/a4d274da-fcb1-4176-b162-db796e39b5cf" />

**Purpose**: Expense-focused view displaying absolute values of negative amounts for clearer financial analysis.

### 🔍 Vendor Search Output
<img width="837" height="253" alt="image" src="https://github.com/user-attachments/assets/e3e982c8-4c00-415d-8f08-1bf2b83e31d8" />

**Purpose**: Case-insensitive vendor search with full transaction details, helping users track spending with specific merchants.

### 📊 Reports Menu Output
<img width="826" height="182" alt="image" src="https://github.com/user-attachments/assets/7665381b-3376-4682-9193-4ff7021a079f" />

**Purpose**: Extended reporting interface with placeholder options for future date-based filtering functionality.

### 📊 Month-to-Date Report Output
<img width="818" height="387" alt="image" src="https://github.com/user-attachments/assets/461b4cba-cde1-4cb3-9af5-339ba7293ab4" />

**Purpose**: Shows all transactions from the first day of the current month to today, helping users track current monthly activity.

### 📅 Previous Month Report Output
<img width="838" height="108" alt="image" src="https://github.com/user-attachments/assets/22cfcb85-3b9a-4f77-a0c0-d2fbdb8a03e6" />

**Purpose**: Displays all transactions from the previous complete month for historical analysis and comparison

### 📈 Year-to-Date Report Output
<img width="820" height="385" alt="image" src="https://github.com/user-attachments/assets/57b9f439-85a6-4d08-8455-4eb8d92c3992" />

**Purpose**: Comprehensive view of all transactions from January 1st of the current year to today for annual financial tracking.

### 🗓️ Previous Year Report Output
<img width="818" height="86" alt="image" src="https://github.com/user-attachments/assets/c918478a-218b-48fb-9383-bdd80ec3b38b" />

**Purpose**: Complete annual view of the previous year's transactions for year-over-year financial comparison.




## Technical Details

### Data Storage
- **Format**: Pipe-delimited CSV file (`transactions.csv`)
- **Structure**: `date|time|description|vendor|amount`
- **Persistence**: Automatic loading on startup and saving after each transaction

### Key Algorithms
- **Reverse Iteration**: Displays newest transactions first
- **Date Range Filtering**: Advanced LocalDate-based filtering for reports
- **Real-time Filtering**: Dynamic filtering of deposits and payments
- **Case-insensitive Search**: Vendor lookup without case sensitivity
- **Amount Processing**: Automatic negative conversion for payments

### Date Handling Features
- **Month-to-Date**: Automatically calculates from 1st of current month to today
- **Previous Month**: Calculates complete previous month regardless of current date
- **Year-to-Date**: From January 1st to current date
- **Previous Year**: Complete calendar year analysis
- **ISO Date Format**: Consistent date parsing using DateTimeFormatter.ISO_LOCAL_DATE

### Error Handling
- File I/O exceptions with user-friendly messages
- Input validation for malformed data
- Graceful application termination
