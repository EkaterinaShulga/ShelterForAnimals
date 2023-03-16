package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.Update;

public interface RecordService {

    Long findRecordId(Update update);

    void saveRecord(Update update);

    void getRecordsForVolunteer(Update update);


}