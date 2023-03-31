package ru.skypro.shelterforanimals.service;


import com.pengrad.telegrambot.model.Update;
import ru.skypro.shelterforanimals.entity.Volunteer;

public interface VolunteerService {


    void sendMessageForUserProbationPeriodIsOver(Update update);

    void saveVolunteer(Volunteer volunteer);

    Volunteer findVolunteer(Long chatId);

    void updateVolunteer(Volunteer volunteer);

    void sendMessageForUserProbationPeriodIsNotOver(Update update);

    void sendMessageForUserAboutExtraTime(Update update);

    void sendMessageForUserAboutBadReport(Update update);


}

