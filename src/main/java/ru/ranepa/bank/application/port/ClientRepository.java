package ru.ranepa.bank.application.port;

import ru.ranepa.bank.domain.Client;

import java.util.List;

public interface ClientRepository {
    void add(Client client);
    List<Client> findAll();
    Client findByName(String name);

}
