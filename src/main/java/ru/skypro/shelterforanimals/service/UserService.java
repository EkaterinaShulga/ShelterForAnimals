package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.Update;
import ru.skypro.shelterforanimals.entity.User;

public interface UserService {

    void createUser(Update update);

    User getUser(Long chatId);
}
