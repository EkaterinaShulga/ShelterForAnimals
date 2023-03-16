package ru.skypro.shelterforanimals.service;


import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import ru.skypro.shelterforanimals.entity.Volunteer;

public interface VolunteerService {


    void sendMessageForAdopter(long chatId);

    void saveVolunteer(Volunteer volunteer);

    Volunteer findVolunteer(Long chatId);

    Volunteer updateVolunteer(Volunteer volunteer);





}

