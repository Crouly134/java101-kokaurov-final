package ru.ranepa.bank.infrastructure;

import ru.ranepa.bank.application.port.AccountRepository;
import ru.ranepa.bank.domain.Account;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountRepository implements AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    public void add(Account account){
        //TODO метод добавляет счёт в список счетов.
    }
    public List<Account> findAll(){
        //TODO метод возвращает все счета.
        return null;
    }
    public List<Account> findByOwner(String owner){
        //TODO метод находит счета по владельцу.
        return null;
    }

}
