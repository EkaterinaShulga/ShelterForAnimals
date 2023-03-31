package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.Update;
import ru.skypro.shelterforanimals.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    void saveContact(Update update);

    void getAllContactsForVolunteer(Update update);

    void deleteAllContacts(Update update);

    Optional<Contact> findContactById(int id);

    List<Contact> getAllContacts();

    Contact addContact(Contact contact);
}
