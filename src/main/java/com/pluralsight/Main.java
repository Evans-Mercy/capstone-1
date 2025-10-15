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
    public static void homeScreen(){
        String choice = "";

        //loop that runs until user chooses to exit (Displays menu repeatedly until user types "X")
        while (!choice.equalsIgnoreCase("X")){
            System.out.println("\n-----------------------");
            System.out.println("Welcome to Mercy's ledger");
            System.out.println("-------------------------");
            System.out.println("[D] Add deposit");
            System.out.println("[P] Make payment");
            System.out.println("[V] View ledger");
            System.out.println("[X] Exit");
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine().trim();

            switch(choice.toUpperCase()) {
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

        //Instantiate a new transaction object with current date and time
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);

        //add new transaction to list
        transactions.add(transaction);

        //append transaction to file and stores
        //use a try-catch to safely open and close file writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))){
            writer.write(transaction.toCsvLine());
            writer.newLine();
            System.out.println("Deposit added successfully!");
        } catch (IOException e){
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
        String amount = scanner.nextLine();

        //to do - decimal place and negative amount
        System.out.println("Deposit added: " + description + " | " + vendor + " | " + amount);
    }

    //Ledger Menu
    public static void ledgerMenu() {
        String choice = "";

        while(!choice.equalsIgnoreCase("H")) {
            System.out.println("\n----Ledger Menu-----");
            System.out.println("[A] All Transactions");
            System.out.println("[D] Deposits");
            System.out.println("[P] Payments");
            System.out.println("[R] Reports");
            System.out.println("[H] Home");
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine().trim();

            switch (choice.toUpperCase()) {
                case "A":
                    Ledger.allTransactions();
                    break;
                case "D":
                    Ledger.deposits();
                    break;
                case "P":
                    Ledger.payments();
                case "R":
                    reportsMenu();
                    break;
                case "H":
                    System.out.println("Back to home page");
                    break;
                default:
                    System.out.println("Invalid option. Try again!");
            }
        }
    }

    //Reports menu
    public static void reportsMenu(){
        String choice = "";

        while (!choice.equals("0")){
            System.out.println("\n ------Reports Menu-------");
            System.out.println("[1] Month To Date");
            System.out.println("[2] Previous Month");
            System.out.println("[3] Year To Date");
            System.out.println("[4] Previous Year");
            System.out.println("[5] Search by Vendor");
            System.out.println("[0] Back to Ledger Menu");
            System.out.println("Enter your choice: ");

            choice= scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Ledger.monthToDate();
                    break;
                case "2":
                    Ledger.previousMonth();
                    break;
                case "3":
                    Ledger.yearToDate();
                    break;
                case "4":
                    Ledger.previousYear();
                    break;
                case "5":
                    Ledger.searchByVendor();
                    break;
                case "0":
                    System.out.println("Back to Ledger Menu");
                    break;
                default:
                    System.out.println("Invalid option. Try again!");
            }
        }
    }
}
