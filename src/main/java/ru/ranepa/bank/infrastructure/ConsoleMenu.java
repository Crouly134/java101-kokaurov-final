package ru.ranepa.bank.infrastructure;

import ru.ranepa.bank.application.AccountService;
import ru.ranepa.bank.application.ClientService;

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
                    0-Close the console menu
                    
                    Please choose one of the menu options
                    """);
            String line  = scanner.nextLine();
            int choice;
            try{
                choice = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please choose one of the menu options (0-6):");
                continue;
            }
            switch (choice){
                case 1 -> handleAddClient();
                case 2 -> handleShowClients();
                case 3 -> handleOpenBankAccount();
                case 4 -> handleShowAllAccounts();
                case 5 -> handleDeposit();
                case 6 -> handleWithdraw();
                case 0 -> running = false;
                default -> System.out.println("There is no such item.");

            }
        }
    }
    private void handleAddClient(){
        //TODO данный метод считывает имя и телефон клиента, вызывает метод clientService.addClient().
    }
    private void handleShowClients(){
        //TODO вызывает метод clientService.findAll() и печатает клиентов.
    }
    private void handleOpenBankAccount(){
        //TODO считывает номер,владельца,баланс и вызывает accountService.openAccount().
    }
    private void handleShowAllAccounts(){
        //TODO вызывает accountService.findAll() и печатает номер,владельца,баланс счёта
    }
    private void handleDeposit(){
        //TODO считывает номер и сумму , вызывает accountService.deposit().
    }
    private void handleWithdraw(){
        //TODO считывает номер и сумму , вызывает accountService.withdraw().
    }
}
