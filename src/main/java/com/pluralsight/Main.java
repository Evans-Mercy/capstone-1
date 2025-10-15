package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    //stores all transactions
    static List<Transaction> transactions = new ArrayList<>();

    //user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //Entry point -Starts the app
        homeScreen();
    }

    //Home screen
    public static void homeScreen() {
        String choice = "";

        //loop that runs until user chooses to exit (Displays menu repeatedly until user types "X")
        while (!choice.equalsIgnoreCase("X")) {
            System.out.println("\n-----------------------");
            System.out.println("Welcome to Mercy's ledger");
            System.out.println("-------------------------");
            System.out.println("[D] Add deposit");
            System.out.println("[P] Make payment");
            System.out.println("[V] View ledger");
            System.out.println("[X] Exit");
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine().trim();

            switch (choice.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "V":
                    //ledgerMenu();
                    break;
                case "X":
                    System.out.println("Exiting ledger.. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again!");
            }
        }
    }

    //Add deposit
    public static void addDeposit() {
        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        //deposit stays positive
        amount = Math.abs(amount);

        //Instantiate a new transaction object with current date and time
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);

        //add new transaction to list
        transactions.add(transaction);

        //append transaction to file and stores
        //use a try-catch to safely open and close file writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            writer.write(transaction.toCsvLine());
            writer.newLine();
            System.out.println("Deposit added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    //Make payment

    public static void makePayment() {

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        //Convert amount to negative to distinguish from deposits
        amount = -Math.abs(amount);

        //instantiate a new transaction object
        Transaction payment = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);

        //add to transaction list
        transactions.add(payment);

        //append to csv file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            writer.write(payment.toCsvLine());
            writer.newLine();
            System.out.println("Payment added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
}

/*
    //Ledger Menu
    public static void ledgerMenu() {
        String choice = "";

        while (!choice.equalsIgnoreCase("H")) {
            System.out.println("\n----Ledger Menu-----");
            System.out.println("[A] All Transactions");
            System.out.println("[D] Deposits");
            System.out.println("[P] Payments");
            System.out.println("[R] Reports");
            System.out.println("[H] Home");
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine().trim();

        }
    }

    //Reports menu
    public static void reportsMenu() {
        String choice = "";

        while (!choice.equals("0")) {
            System.out.println("\n ------Reports Menu-------");
            System.out.println("[1] Month To Date");
            System.out.println("[2] Previous Month");
            System.out.println("[3] Year To Date");
            System.out.println("[4] Previous Year");
            System.out.println("[5] Search by Vendor");
            System.out.println("[0] Back to Ledger Menu");
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine().trim();

        }
    }
*/

