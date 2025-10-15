package com.pluralsight;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.Scanner;

public class AccountingLedgerApp {

    //user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //Starts the app
        homeScreen();
    }

    //Home screen
    public static void homeScreen(){
        String choice = "";

        //loop that runs until user chooses to exit
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
                    //makePayment();
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
        String amount = scanner.nextLine();

        System.out.println("Deposit added: " + description + " | " + vendor + " | " + amount);
        //To do: add negative amount in csv
    }
}
