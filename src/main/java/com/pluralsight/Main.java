package com.pluralsight;

import java.io.*;
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

    //Load transactions from csv file
    public static void loadTransactionsFromFile() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //instantiate a new transaction object with all values from this line and adds to list
                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("No previous transactions");
        }
    }

    public static void main(String[] args) {
        //Entry point -Starts the app
        loadTransactionsFromFile();
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
                    ledgerMenu();
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
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(transaction.toCsvLine());
            bufferedWriter.newLine();
            bufferedWriter.close();

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
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv");
            BufferedWriter bufferedwriter = new BufferedWriter(fileWriter);

            bufferedwriter.write(payment.toCsvLine());
            bufferedwriter.newLine();

            System.out.println("Payment added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    //Ledger Menu
    public static void ledgerMenu() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        String choice = "";

        //loops until user exits
        while (!choice.equalsIgnoreCase("H")) {

            System.out.println("\n-----Ledger Menu-----");
            System.out.println("[A] All transactions");
            System.out.println("[D] Deposits ");
            System.out.println("[P] Payments");
            System.out.println("[H] Home");
            System.out.println("Enter your choice: ");
            choice = scanner.nextLine().trim();

            //
            switch (choice.toUpperCase()) {
                case "A":
                    showAllTransactions();
                    break;
                case "D":
                    showDeposits();
                    break;
                case "P":
                    showPayments();
                    break;
                case "H":
                    break;
                default:
                    System.out.println("Invalid option. Try again!");
            }

            //todo
            // sort to show newest first
        }
    }

    //displays all transactions
    public static void showAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
            return;
        }

        System.out.println("\nAll transactions:");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            String amountString;
            if (t.getAmount() >= 0) {
                amountString = "+" + t.getAmount();
            } else {
                amountString = "" + t.getAmount();
            }
            System.out.println(t.getDate() + " | " + t.getTime() + " | "  + t.getDescription() + " | " + t.getVendor() + " | " + amountString);
        }
    }

    //Show only deposits
    public static void showDeposits() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n--------Deposit---------");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getAmount() >= 0) {
                String amountString = "+" + t.getAmount();
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getVendor() + " | " + amountString);
            }
        }
    }

    //show negative payments
    public static void showPayments() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        }

        System.out.println("\n--------Payments---------");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                String amountString = "" + t.getAmount();
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getVendor() + " | " + amountString);
            }
        }
    }
}
/*
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

