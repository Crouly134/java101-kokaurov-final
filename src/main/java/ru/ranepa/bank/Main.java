package ru.ranepa.bank;

import ru.ranepa.bank.application.AccountService;
import ru.ranepa.bank.application.ClientService;
import ru.ranepa.bank.application.port.AccountRepository;
import ru.ranepa.bank.application.port.ClientRepository;
import ru.ranepa.bank.infrastructure.ConsoleMenu;
import ru.ranepa.bank.infrastructure.InMemoryAccountRepository;
import ru.ranepa.bank.infrastructure.InMemoryClientRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to the bank branch.");

        ClientRepository clientRepository = new InMemoryClientRepository();
        ClientService clientService = new ClientService(clientRepository);

        AccountRepository accountRepository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(accountRepository);

        Scanner scanner = new Scanner(System.in);
        ConsoleMenu menu = new ConsoleMenu(scanner,accountService,clientService);
        menu.run();
    }
}