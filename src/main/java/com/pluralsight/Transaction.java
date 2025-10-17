package com.pluralsight;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    //My helper methods

    public String toCsvString() {
        // making our pipe-delimited CSV String: date|time|description|vendor|amount
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    public LocalDate getLocalDate() {
        return LocalDate.parse(this.date, DateTimeFormatter.ISO_LOCAL_DATE); //Workbook 3a (pg32)
        // ISO_LOCAL_DATE matches the default format of LocalDate.toString() (YYYY-MM-DD)

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
