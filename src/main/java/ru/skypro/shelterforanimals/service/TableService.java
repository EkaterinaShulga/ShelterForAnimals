package ru.skypro.shelterforanimals.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public interface TableService {

    InlineKeyboardMarkup startMenuButtonsForShelterDog();
    InlineKeyboardMarkup startMenuButtonsForShelterCat() ;
    InlineKeyboardMarkup menuButtonsWithInformationAboutShelterForDog();
    InlineKeyboardMarkup menuButtonsWithInformationAboutShelterForCat();
    InlineKeyboardMarkup menuButtonsWithInformationAboutGod();
    InlineKeyboardMarkup menuButtonsWithInformationAboutCat();
    InlineKeyboardMarkup userStatusMenuButtons();
    InlineKeyboardMarkup shelterStatusMenuButtons();
    InlineKeyboardMarkup volunteerFunctionality();
    InlineKeyboardMarkup menuForVolunteer();
    InlineKeyboardMarkup makeButtonsForMenuStageThreeForReport();
}
