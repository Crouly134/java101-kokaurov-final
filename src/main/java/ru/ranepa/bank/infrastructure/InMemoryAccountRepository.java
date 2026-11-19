package ru.ranepa.bank.infrastructure;

import ru.ranepa.bank.application.port.AccountRepository;
import ru.ranepa.bank.domain.Account;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountRepository implements AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    @Override
    public void add(Account account){
        accounts.add(account);
    }
    @Override
    public List<Account> findAll(){
        return new ArrayList<>(accounts);
    }
    @Override
    public List<Account> findByOwner(String owner){
        List<Account> result = new ArrayList<>();
        for(Account account : accounts){
            if(account.getOwner().equalsIgnoreCase(owner)){
                result.add(account);
            }
        }
        return result;
    }

}
