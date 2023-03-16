package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;

import java.io.IOException;

public interface PetPhotoService {
    void uploadPhoto(Long recordId, PhotoSize[] photos) throws IOException;
    String getExtensions(String fileName);

    void photo(Update update) throws IOException;
}

