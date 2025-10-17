package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Represents one transaction
public class Transaction implements Comparable<Transaction>{

    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    //Constructor
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    //Getters

    public LocalDate getDate() {

        return date;
    }

    public LocalTime getTime() {

        return time;
    }

    public String getDescription() {

        return description;
    }

    public String getVendor() {

        return vendor;
    }

    public double getAmount() {

        return amount;
    }

    //sorting newest first
    @Override
    public int compareTo(Transaction otherTransaction) {
        LocalDateTime thisDateTime = LocalDateTime.of(this.date, this.time);
        LocalDateTime otherDateTime = LocalDateTime.of(otherTransaction.getDate(), otherTransaction.getTime());
        return otherDateTime.compareTo(thisDateTime);
    }

    //converts the transaction into a formatted line for easy saving to csv file
    public String toCsvLine() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return date.format(dateFormatter) + "|" + time.format(timeFormatter) + "|" + description + "|" + vendor + "|" + amount;
    }

    //To string method to display on screen
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return  date.format(dateFormatter) + "|" + time.format(timeFormatter) + "|" + description + "|" + vendor + "|" +  amount ;
    }
}
