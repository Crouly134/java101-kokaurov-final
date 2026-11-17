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
        //TODO метод проверяет имя(пустое или нет) и добавляет клиента в репозиторий.
        return false;
    }
    public List<Client> findAll(){
        //TODO возращает список клиентов из репозитория.
        return null;
    }
    public Client findByName(String name){
        //TODO метод находит клиента по имени.
        return null;
    }
}
