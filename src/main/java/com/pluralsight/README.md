# Accounting Ledger App
This app is for tracking deposits and payments.

## Features:
1. Adding a deposit
2. Making payment
3. Store transactions
4. Display transactions
5. Reports Menu that generates:
        -Month to date
        - Previous month
        - Year to date
        - Previous Year
        - Search by Vendor
6. Interactive Home screen - Navigate using menu options


## Classes:
1. Main
    - Display menus
    - Takes user input
    - Creates transaction objects
    - Stores transactions in an arraylist
    - Saves to and reads from transactions.csv
   
2. Transaction
    - Represents one transaction record
    - Stores: 
            - date, time, description, vendor, amount
    - formats itself as a CSV line so you can save it easily

## How It Works

- When the app starts, it loads all transactions from transactions.csv.

- Users interact through a text-based menu (Deposit, Payment, Ledger, Reports, Exit).

- Each new transaction is written to both memory and the CSV file.

- Sorting and filters are handled directly in memory for faster access.

## Challenges & Solutions

- Formatting the time - used 'DateTimeFormatter', & import the class.

- Deciding on where to store the transactions - created an array list in the main to avoid reading the file every time. Modified my classes

- Transaction list starts empty when running view ledger even if transactions.csv has data - added a method to load existing transactions from CSV into memory

- Transactions not loading from file - added a method to load CSV data into the arraylist 

- Sorting newest to oldest - used Comparable and collections.sort.

- Differentiating payments and deposits - made payments negative using -Math.abs(amount)





  