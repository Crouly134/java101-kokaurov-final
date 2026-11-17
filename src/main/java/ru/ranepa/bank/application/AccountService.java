package ru.ranepa.bank.application;

import ru.ranepa.bank.application.port.AccountRepository;
import ru.ranepa.bank.domain.Account;

import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public boolean openAccount(String number, String owner, double balance){
        //TODO
        return false;
    }
    public List<Account> findAll(){
        //TODO
        return null;
    }
    public List<Account> findByOwner(String owner){
        //TODO
        return null;
    }
    public boolean deposit(String number, double amount){
        //TODO
        return false;
    }
    public boolean withdraw(String number, double amount){
        //TODO
        return false;
    }
}
