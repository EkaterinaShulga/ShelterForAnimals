package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public interface ReportService {


    void getReportsForVolunteer(Update update);

    void saveReport(Update update);
}
