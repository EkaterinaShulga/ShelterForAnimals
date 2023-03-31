package ru.skypro.shelterforanimals.service;

import ru.skypro.shelterforanimals.entity.Client;

public interface ClientService {
    void saveClient(Client client);

    Client findClient(Long chatId);

    void updateClient(Client client);
}
