# Accounting Ledger App
This app is for tracking deposits and payments.

## Features:
1. Adding a deposit
2. Storing transactions
3. Displaying transactions
4. Interactive Home Screen with menu options

## Classes:
1. Main
    - Display menus
    - Takes user input
    - Creates transaction objects
    - Stores transactions in an arraylist
    - Saves to and reads from transactions.csv
   
2. Transaction
    - Represents one transaction record
    - Holds: 
            - date, time, description, vendor, amount
    - formats itself as a CSV line so you can save it easily

Challenges & Solutions

- Formatting the time - used 'DateTimeFormatter', & import the class.

- Deciding on where to store the transactions - created an array list in the main to avoid reading the file every time. Modified my classes
- Transaction list starts empty when running view ledger even if transactions.csv has data - added a method to load existing transactions from CSV into memory
  