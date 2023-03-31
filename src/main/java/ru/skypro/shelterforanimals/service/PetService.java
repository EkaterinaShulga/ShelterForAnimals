package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.Update;

public interface PetService {

    void savePet(Update update);

    void saveDog(String name, String color, String age, Long chatId);

    void saveCat(String name, String color, String age, Long chatId);


}
