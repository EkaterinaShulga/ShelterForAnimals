package ru.skypro.shelterforanimals.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Client;
import ru.skypro.shelterforanimals.repository.ClientRepository;
import ru.skypro.shelterforanimals.service.ClientService;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    /**
     * saves the client in the database
     *
     * @param client - entity
     */
    @Override
    public void saveClient(Client client) {
        log.info("метод saveClient - сохраняем пользователя - сlientServiceImpl");
        clientRepository.save(client);
    }

    /**
     * saves the client in the database
     *
     * @param chatId - chatId client
     */
    @Override
    public Client findClient(Long chatId) {
        log.info("метод findClient - ищем пользователя - сlientServiceImpl");
        return clientRepository.findByChatId(chatId);
    }

    @Override
    public void updateClient(Client client) {
        log.info("метод updateClient - изменяем данные пользователя - сlientServiceImpl");
        Client clientToUpdate = clientRepository.findByChatId(client.getChatId());
        clientToUpdate.setStatus(client.getStatus());
        clientToUpdate.setChatId(client.getChatId());
        clientRepository.save(clientToUpdate);
    }


}


