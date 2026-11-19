package ru.ranepa.bank.infrastructure;

import ru.ranepa.bank.application.port.ClientRepository;
import ru.ranepa.bank.domain.Client;

import java.util.ArrayList;
import java.util.List;

public class InMemoryClientRepository implements ClientRepository {
    private final List<Client> clients = new ArrayList<>();

    @Override
    public void add(Client client){
        clients.add(client);
    }
    @Override
    public List<Client> findAll(){
        return new ArrayList<>(clients);
    }
    @Override
    public Client findByName(String name){
        for(Client client : clients){
            if(client.getName().equalsIgnoreCase(name)){
                return client;
            }
        }
        return null;
    }
}
