package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Ledger {
    //file path for all transactions
    private static final String transactionFile = "src/main/resources/transactions.csv";


    //to do
    //read all transactions
    //display all transactions - array list?
    //sort newest first by date & time
    //show deposits only
    //show payments only
    //search transactions by vendor

    //add a transaction
    public static void addTransaction(Transactions t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile, true))){
            writer.write(t.toCsvLine());
            writer.newLine();
        }catch (IOException e){
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    //all transactions
    public static void allTransactions() {
        System.out.println("All transactions: ");

    }
    public static void deposits() {
        System.out.println("Deposits: ");

    }
    public static void payments() {
        System.out.println("Payments: ");

    }
    public static void monthToDate() {
        System.out.println("Month to Date report: ");

    }
    public static void previousMonth() {
        System.out.println("Previous Month report: ");

    }
    public static void yearToDate() {
        System.out.println("Year to Date report: ");

    }
    public static void previousYear() {
        System.out.println("Previous Year report: ");

    }
    public static void searchByVendor() {
        System.out.println("Searching transactions by vendor: ");

    }
}
