package ru.ranepa.bank.infrastructure;

import ru.ranepa.bank.application.AccountService;
import ru.ranepa.bank.application.ClientService;
import ru.ranepa.bank.domain.Account;
import ru.ranepa.bank.domain.Client;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;
    private final AccountService accountService;
    private final ClientService clientService;

    public ConsoleMenu(Scanner scanner, AccountService accountService, ClientService clientService){
        this.scanner = scanner;
        this.accountService = accountService;
        this.clientService = clientService;
    }
    public void run(){
        boolean running = true;
        while(running) {
            System.out.println("""
                    
                    BANK BRANCH
                    
                    1-Add Client
                    2-Show Clients
                    3-Open bank account
                    4-Show all accounts of the branch
                    5-Top up the account
                    6-Withdraw from the account
                    7-Transfer between accounts
                    8-Find accounts by owner
                    9-Show accounts with non-zero balance
                    10-Statistics
                    11-Find account by name
                    0-Close the console menu
                    
                    Please choose one of the menu options
                    """);
            String line  = scanner.nextLine();
            int choice;
            try{
                choice = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please choose one of the menu options (0-10):");
                continue;
            }
            switch (choice){
                case 1 -> handleAddClient();
                case 2 -> handleShowClients();
                case 3 -> handleOpenBankAccount();
                case 4 -> handleShowAllAccounts();
                case 5 -> handleDeposit();
                case 6 -> handleWithdraw();
                case 7 -> handleTransfer();
                case 8 -> handleFindByOwner();
                case 9 -> handleNonZero();
                case 10 -> handleStatistics();
                case 11 -> handleFindClient();
                case 0 -> running = false;
                default -> System.out.println("There is no such item.");

            }
        }
    }
    private void handleAddClient(){
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Phone: ");
        String phone = scanner.nextLine();
        if(clientService.addClient(name,phone)){
            System.out.println("Client added.");
        }else {
            System.out.println("Name cannot be empty. Client not added.");
        }
    }
    private void handleShowClients(){
        List<Client> clients = clientService.findAll();
        if(clients.isEmpty()){
            System.out.println("No clients yet.");
            return;
        }
        for(Client client: clients){
            System.out.println(client.getName()+" - "+client.getPhone());
        }
    }
    private void handleOpenBankAccount(){
        System.out.println("1-Savings account, 2-Credit account, 3-Only owner");
        System.out.println("Choose type: ");
        String type = scanner.nextLine().trim();
        switch (type){
            case "1" ->openSavings();
            case "2" ->openCredit();
            case "3" ->openOnlyOwner();
            default -> System.out.println("Unknown account type.");
        }
    }

    private void openSavings(){
        System.out.println("Number: ");
        String number = scanner.nextLine();
        System.out.println("Owner: ");
        String owner = scanner.nextLine();
        System.out.println("Balance: ");
        double balance = readDouble();
        System.out.println("Interest rate: ");
        double rate = readDouble();
        if(accountService.openSavingsAccount(number,owner,balance,rate)){
            System.out.println("Savings account opened.");
        }else {
            System.out.println("Invalid data(number taken, owner empty, balance<0)");
        }
    }

    private void openCredit(){
        System.out.println("Number: ");
        String number = scanner.nextLine();
        System.out.println("Owner: ");
        String owner = scanner.nextLine();
        System.out.println("Balance: ");
        double balance = readDouble();
        System.out.println("Limit: ");
        double limit = readDouble();
        System.out.println("Interest rate: ");
        double rate = readDouble();
        if(accountService.openCreditAccount(number,owner,balance,limit,rate)){
            System.out.println("Credit account opened.");
        }else {
            System.out.println("Invalid data(number taken, owner empty, balance<0)");
        }
    }

    private void openOnlyOwner(){
        System.out.println("Owner: ");
        String owner = scanner.nextLine();
        if(accountService.openAccount(owner)){
            System.out.println("Account opened with default settings.");
        }else {
            System.out.println("Owner cannot be empty.");
        }
    }

    private void handleShowAllAccounts(){
        List<Account> accounts = accountService.findAll();
        if(accounts.isEmpty()){
            System.out.println("No accounts yet.");
            return;
        }
        for(Account account : accounts){
            System.out.println(account.describe());
        }
    }

    private void handleDeposit(){
        System.out.println("Account number: ");
        String number = scanner.nextLine();
        System.out.println("Amount: ");
        double amount = readDouble();
        if(accountService.deposit(number,amount)){
            System.out.println("Account topped up.");
        }else {
            System.out.println("Failed: wrong number or amount <= 0.");
        }
    }

    private void handleWithdraw(){
        System.out.println("Account number: ");
        String number = scanner.nextLine();
        System.out.println("Amount: ");
        double amount = readDouble();
        if(accountService.withdraw(number,amount)){
            System.out.println("Money withdrawn.");
        }else {
            System.out.println("Failed: wrong number, amount <= 0 or not enough money.");
        }
    }

    private void handleTransfer(){
        System.out.println("From account: ");
        String from = scanner.nextLine();
        System.out.println("To account: ");
        String to = scanner.nextLine();
        System.out.println("Amount: ");
        double amount = readDouble();
        if(accountService.transfer(from,to,amount)){
            System.out.println("Transfer completed.");
        }else {
            System.out.println("Failed: wrong numbers, same account, amount <= 0 or not enough money.");
        }
    }

    private void handleFindByOwner(){
        System.out.println("Owner name: ");
        String owner = scanner.nextLine();
        List<Account> found = accountService.findByOwner(owner);
        if(found.isEmpty()){
            System.out.println("No accounts found for this owner.");
            return;
        }
        for(Account account: found){
            System.out.println(account.describe());
        }
    }

    private void handleNonZero(){
        List<Account> accounts = accountService.findWithNonZeroBalance();
        if(accounts.isEmpty()){
            System.out.println("No accounts with non-zero balance.");
            return;
        }
        for(Account account : accounts){
            System.out.println(account.describe());
        }
    }

    private void handleStatistics(){
        System.out.println("Total balance: "+ accountService.totalBalance());
        System.out.println("Accounts opened: "+accountService.accountsCount());
        Account largest = accountService.largestAccount();
        if(largest == null){
            System.out.println("Largest account: None(no accounts).");
        }else {
            System.out.println("Largest account: " + largest.describe());
        }
    }

    private void handleFindClient(){
        System.out.println("Client name: ");
        String name = scanner.nextLine();
        Client client = clientService.findByName(name);
        if(client == null){
            System.out.println("Client not found.");
        }else {
            System.out.println(client.getName()+" - "+client.getPhone());
        }
    }

    private double readDouble() {
        while (true) {
            String line = scanner.nextLine();
            try {
                return Double.parseDouble(line.trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number: ");
            }
        }
    }
}
