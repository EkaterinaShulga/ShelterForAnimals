package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.Update;
import ru.skypro.shelterforanimals.entity.Record;

public interface RecordService {

    Long findRecordId(Update update);

    void saveRecord(Update update);

    void getRecordsForVolunteer(Update update);

    Record findById(Long recordId);


}