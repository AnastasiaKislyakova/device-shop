package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.model.Client;

import java.util.List;

public interface ClientService {
    Client saveClient(Client client);

    Client getClientById(long id);

    List<Client> getClients(ClientFilter filter);
}

