package ru.ranepa.bank.application;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.ranepa.bank.application.port.ClientRepository;
import ru.ranepa.bank.domain.Client;
import ru.ranepa.bank.infrastructure.InMemoryClientRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {
    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    void setUp(){
        clientRepository = new InMemoryClientRepository();
        clientService = new ClientService(clientRepository);
    }

    @Test
    void addClient_success(){
        assertTrue(clientService.addClient("Ivan","+79181763312"));
        assertEquals(1,clientService.findAll().size());
    }
    @Test
    void addClient_withEmptyName_returnsFalse(){
      assertFalse(clientService.addClient("   ","+79815467845"));
       assertFalse(clientService.addClient(null,"+79186456574"));
       assertEquals(0,clientService.findAll().size());
    }
    @Test
    void findByName_returnsClient(){
        clientService.addClient("Ivan","+79816543452");
        Client found = clientService.findByName("Ivan");
        assertNotNull(found);
        assertEquals("+79816543452",found.getPhone());
    }
    @Test
    void findByName_isCaseInsensitive(){
        clientService.addClient("Ivan","+79816543452");
        assertNotNull(clientService.findByName("Ivan"));
    }
    @Test
    void findByName_withUnknownName_returnsNull(){
        clientService.addClient("Ivan","+79816543452");
        assertNull(clientService.findByName("Petr"));
    }
    @Test
    void findByName_withEmptyName_returnsNull(){
       assertNull(clientService.findByName("  "));
        assertNull(clientService.findByName(null));
    }
    @Test
    void findByName_returnsAllAddedClients(){
        clientService.addClient("Ivan","+79816543452");
        clientService.addClient("Petr","+79816343452");
        List<Client> all = clientService.findAll();
        assertEquals(2,all.size());
    }



}
