package ru.ranepa.bank.application;

import ru.ranepa.bank.application.port.AccountRepository;
import ru.ranepa.bank.domain.Account;
import ru.ranepa.bank.domain.CreditAccount;
import ru.ranepa.bank.domain.SavingsAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;
    private static int openedCount = 0;

    public AccountService(AccountRepository accountRepository){

        this.accountRepository = accountRepository;
    }

    private Account findByNumber(String number){
        if(number == null){
            return null;
        }
        for( Account account: accountRepository.findAll()){
            if (account.getNumber().equalsIgnoreCase(number.trim())){
                return account;
            }
        }
        return null;
    }

    private boolean isValidNewAccount(String number,String owner, double balance){
        if(number == null || number.trim().isEmpty()){
            return false;
        }
        if(owner == null || owner.trim().isEmpty()){
            return false;
        }
        if(balance < 0){
            return false;
        }
        return findByNumber(number) == null;
    }

    private String generateNumber(){
        return "A" + (openedCount+1);
    }

    public boolean openSavingsAccount(String number, String owner, double balance,double interestRate){
        if(!isValidNewAccount(number, owner, balance)) {
            return false;
        }
        accountRepository.add(new SavingsAccount(number.trim(),owner.trim(),balance,interestRate));
        openedCount++;
        return true;
    }

    public boolean openCreditAccount(String number, String owner, double balance, double limit, double interestRate){
        if(!isValidNewAccount(number,owner,balance)){
            return false;
        }
        accountRepository.add(new CreditAccount(number.trim(), owner.trim(), balance, limit, interestRate));
        openedCount++;
        return true;
    }

    public boolean openAccount(String owner){
        if (owner == null || owner.trim().isEmpty()){
            return false;
        }
        String number = generateNumber();
        while (findByNumber(number) != null){
            openedCount++;
            number = generateNumber();
        }
        accountRepository.add(new SavingsAccount(number, owner.trim(),0.0,0.0));
        openedCount++;
        return true;
    }

    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    public List<Account> findByOwner(String owner){
    if(owner == null || owner.trim().isEmpty()){
        return new ArrayList<>();
    }
    return accountRepository.findByOwner(owner.trim());
    }

    public List<Account> findWithNonZeroBalance(){
        List<Account> result = new ArrayList<>();
        for(Account account : accountRepository.findAll()){
            if(account.getBalance() != 0 ){
                result.add(account);
            }
        }
        return result;
    }

    public double totalBalance(){
        double sum = 0;
        for(Account account : accountRepository.findAll()){
            sum+=account.getBalance();
        }
        return sum;
    }

    public int accountsCount(){
        return openedCount;
    }

    public Account largestAccount(){
        Account largest = null;
        for(Account account : accountRepository.findAll()){
            if(largest == null || account.getBalance() > largest.getBalance()){
                largest = account;
            }
        }
        return largest;
    }

    public boolean deposit(String number, double amount){
        if (amount <= 0){
            return false;
        }
        Account account = findByNumber(number);
        if (account == null){
            return false;
        }
        account.setBalance(account.getBalance()+amount);
        return true;
    }

    public boolean withdraw(String number, double amount){
        if(amount <= 0 ){
            return false;
        }
        Account account = findByNumber(number);
        if(account == null){
            return false;
        }
        if(account.getBalance()<amount){
            return false;
        }
        account.setBalance(account.getBalance()-amount);
        return true;
    }

    public boolean transfer(String fromNumber,String toNumber, double amount){
        if(amount <= 0){
            return false;
        }
        if(fromNumber == null || toNumber == null){
            return false;
        }
        if(fromNumber.equalsIgnoreCase(toNumber)){
            return false;
        }
        Account from = findByNumber(fromNumber);
        Account to = findByNumber(toNumber);
        if (from == null || to == null){
            return false;
        }
        if(from.getBalance()<amount){
            return false;
        }
        from.setBalance(from.getBalance()- amount);
        to.setBalance(to.getBalance()+amount);
        return true;
    }
}
