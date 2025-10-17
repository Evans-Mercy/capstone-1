package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    //stores all transactions
    static List<Transaction> transactions = new ArrayList<>();

    //user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //Entry point -Starts the app
        loadTransactionsFromFile();
        homeScreen();
    }

    //Load transactions from csv file
    public static void loadTransactionsFromFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
            String line;
            //skips header
            bufferedReader.readLine();

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
                    //System.out.println("testing" + transactions);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
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
            System.out.println("[L] Ledger");
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
                case "L":
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

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        //Instantiate a new transaction object with current date and time
        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        //add new transaction to list
        transactions.add(transaction);

        //append transaction to file and stores
        //use a try-catch to safely open and close file writer
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

            bufferedWriter.write(transaction.toCsvLine());
            bufferedWriter.newLine();

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
        try (
                BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

            bufferedwriter.write(payment.toCsvLine());
            bufferedwriter.newLine();

            System.out.println("Payment added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    //Ledger Menu
    public static void ledgerMenu() {

        String choice = "";

        //loops until user exits
        while (!choice.equalsIgnoreCase("H")) {

            System.out.println("\n-----Ledger Menu-----");
            System.out.println("[A] All Entries");
            System.out.println("[D] Deposits");
            System.out.println("[P] Payments");
            System.out.println("[R] Reports");
            System.out.println("[H] Home");
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine().trim();

            switch (choice.toUpperCase()) {
                case "A":
                    showAllEntries();
                    break;
                case "D":
                    showDeposits();
                    break;
                case "P":
                    showPayments();
                    break;
                case "R":
                    reportsMenu();
                    break;
                case "H":
                    break;
                default:
                    System.out.println("Invalid option. Try again!");
            }
        }
    }

    //displays all transactions
    public static void showAllEntries() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        //sort transactions newest first
        Collections.sort(transactions);

        System.out.println("\nAll transactions:");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            String amountString;
            if (t.getAmount() >= 0) {
                amountString = "+" + t.getAmount(); //deposit
            } else {
                amountString = "" + t.getAmount();
            }
            System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getVendor() + " | " + amountString);
        }
    }

    //Show only deposits
    public static void showDeposits() {
        if (transactions.isEmpty()) {
            System.out.println("No transaction found");
            return;
        }
        System.out.println("\n--------Deposit---------");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getAmount() >= 0) {
                String amountString = "+" + t.getAmount(); //deposit
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getVendor() + " | " + amountString);
            }
        }
    }

    //shows negative payments
    public static void showPayments() {
        if (transactions.isEmpty()) {
            System.out.println("No transaction found");
            return;
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

    //Reports menu
    public static void reportsMenu() {
        String choice = "";

        while (!choice.equals("6")) {
            System.out.println("\n------Reports Menu------");
            System.out.println("[1] Month to Date");
            System.out.println("[2] Previous Month");
            System.out.println("[3] Year to Date");
            System.out.println("[4] Previous Year");
            System.out.println("[5] Search by Vendor");
            System.out.println("[6] Back to Ledger");
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    MonthToDate();
                    break;
                case "2":
                    previousMonth();
                    break;
                case "3":
                    yearToDate();
                    break;
                case "4":
                    previousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "6":
                    break;
                default:
                    System.out.println("Invalid option. Try again!");
            }
        }
    }

    public static void MonthToDate() {
        //gets today's date
        LocalDate today = LocalDate.now();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            if (t.getDate().getMonth() == today.getMonth() && t.getDate().getYear() == today.getYear()) {
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getVendor() + " | " + t.getAmount());
            }
        }
    }

    public static void previousMonth() {
        //today's date
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        //previous month and year
        int previousMonth = currentMonth - 1;
        int yearOfPreviousMonth = currentYear;

        if (previousMonth == 0) {
            previousMonth = 12;
            yearOfPreviousMonth = currentYear - 1;
        }

        //header
        System.out.println("\nTransactions from previous month: ");
        System.out.println("Date | Time | Description | Vendor | Amount");

        //loop through transactions
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            if (t.getDate().getMonthValue() == previousMonth && t.getDate().getYear() == yearOfPreviousMonth) {
                String amountString;
                if (t.getAmount() >= 0) {
                    amountString = "+" + t.getAmount();
                } else {
                    amountString = "" + t.getAmount();
                }
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + amountString);
            }
        }
    }

    public static void yearToDate() {
        //today's date
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        //header
        System.out.println("\nTransactions from Year to date: ");
        System.out.println("Date | Time | Description | Vendor | Amount");

        //loop through transactions
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            if (t.getDate().getYear() == currentYear) {
                String amountString;
                if (t.getAmount() >= 0) {
                    amountString = "+" + t.getAmount();
                } else {
                    amountString = "" + t.getAmount();
                }
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + amountString);
            }
        }
    }

    //previous year
    public static void previousYear() {
        //today's date
        LocalDate today = LocalDate.now();
        int previousYear = today.getYear() - 1;

        //header
        System.out.println("\nTransactions from Previous Year: ");
        System.out.println("Date | Time | Description | Vendor | Amount");

        //loop through transactions
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            if (t.getDate().getYear() == previousYear) {
                String amountString;
                if (t.getAmount() >= 0) {
                    amountString = "+" + t.getAmount();
                } else {
                    amountString = "" + t.getAmount();
                }
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + amountString);
            }
        }
    }

    //search by vendor
    public static void searchByVendor(){
        System.out.println("\n Enter vendor name: ");
        String searchVendor = scanner.nextLine().trim();

        System.out.println("\n Transactions for vendor: " + searchVendor);
        System.out.println("Date | Time | Description | Vendor | Amount");

        boolean found = false;

        for (int i = 0; i < transactions.size(); i++){
            Transaction t = transactions.get(i);

            if(t.getVendor().equalsIgnoreCase(searchVendor)){
                found = true;
                String amountString;
                if (t.getAmount() >= 0) {
                    amountString = "+" + t.getAmount();
                } else {
                    amountString = "" + t.getAmount();
                }
                System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + amountString);


            }
        }
        if (!found){
            System.out.println("No transactions found for that vendor.");
        }

    }
}



