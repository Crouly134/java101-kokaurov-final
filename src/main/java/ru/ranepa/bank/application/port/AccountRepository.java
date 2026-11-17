package ru.ranepa.bank.application.port;

import ru.ranepa.bank.domain.Account;

import java.util.List;

public interface AccountRepository {
    void add(Account account);
    List<Account> findAll();
    List<Account> findByOwner(String owner);
}
