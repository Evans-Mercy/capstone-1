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

    public static void loadTransactionsFromFile() {

        //fileScanner reads the file line by line - tells which file to read
        try(Scanner fileScanner = new Scanner(new java.io.File("transactions.csv"))){

            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
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
        } catch (Exception e){
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


    //Ledger Menu
    public static void ledgerMenu() {

       if (transactions.isEmpty()){
           System.out.println("No transactions found.");
           return;
       }
       System.out.println("\n--------Ledger Menu-------");
       System.out.println("Date       | Time  | Description | Vendor | Amount");

       //todo
        // sort to show newest first
        for (Transaction t : transactions) {

            System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getVendor() + " | " + t.getAmount());
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

