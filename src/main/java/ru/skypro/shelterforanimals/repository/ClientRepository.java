package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByChatId(Long chatId);

}
