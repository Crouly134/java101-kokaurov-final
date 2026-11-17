package ru.ranepa.bank.infrastructure;

import ru.ranepa.bank.application.port.ClientRepository;
import ru.ranepa.bank.domain.Client;

import java.util.ArrayList;
import java.util.List;

public class InMemoryClientRepository implements ClientRepository {
    private final List<Client> clients = new ArrayList<>();

    public void add(Client client){
        //TODO Данный метод добавляет клиента в список клиентов.
    }
    public List<Client> findAll(){
        //TODO метод возвращает список клиентов при обращение.
        return null;
    }
    public Client findByName(String name){
        //TODO метод находит определенного клиента по имени.
        return null;
    }
}
