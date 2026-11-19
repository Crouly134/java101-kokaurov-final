package ru.ranepa.bank.application;

import ru.ranepa.bank.application.port.ClientRepository;
import ru.ranepa.bank.domain.Client;

import java.util.List;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public boolean addClient(String name, String phone){
        if(name == null || name.trim().isEmpty()){
            return false;
        }
        clientRepository.add(new Client(name.trim(), phone));
        return true;
    }
    public List<Client> findAll(){
        return clientRepository.findAll();
    }
    public Client findByName(String name){
        if (name == null || name.trim().isEmpty()){
            return null;
        }
        return clientRepository.findByName(name.trim());
    }
}
