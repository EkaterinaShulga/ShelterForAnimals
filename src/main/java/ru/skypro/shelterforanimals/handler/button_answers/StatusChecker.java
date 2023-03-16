package ru.skypro.shelterforanimals.handler.button_answers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Client;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.service.ClientService;
import ru.skypro.shelterforanimals.service.TableService;
import ru.skypro.shelterforanimals.service.VolunteerService;

import static ru.skypro.shelterforanimals.constants.BotButtonEnum.BUTTON_VOLUNTEER_FOR_CAT_SHELTER;
import static ru.skypro.shelterforanimals.constants.BotButtonEnum.BUTTON_VOLUNTEER_FOR_DOG_SHELTER;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatusChecker {
    private final TelegramBot telegramBot;
    private final TableService tableService;
    private final VolunteerService volunteerService;
    private final ClientService clientService;


    private void checkButtonAnswerForStatus(Update update) {
        log.info("Проверка ответа Пользователь/Волонтер - checkButtonAnswerForStatus - StatusChecker");
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        int messageId = update.callbackQuery().message().messageId();
        long chatId = update.callbackQuery().message().chat().id();
        switch (callBackData) {
            case "Волонтер":
                //вызов  ответа для волонтера
                telegramBot.execute(new EditMessageText(chatId,messageId, START_MESSAGE_VOLUNTEER.getMessage()).
                        replyMarkup(tableService.menuForVolunteer()));
                log.info("Отправил меню для волонтера");
                break;
            case "Пользователь":
                telegramBot.execute(new EditMessageText(chatId,messageId,
                        START_SHELTER_OPTION.getMessage()).replyMarkup(tableService.shelterStatusMenuButtons()));
                log.info("Отправил пользователю меню для выбора приюта");
                break;
        }
    }

    public void checkButtonShelterStatus(Update update) {
        log.info("Проверка выбора приюта и создание клиента -  checkButtonShelterStatus - StatusChecker");
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        int messageId = update.callbackQuery().message().messageId();
        long chatId = update.callbackQuery().message().chat().id();
        Client client = new Client();
        client.setChatId(chatId);
        client.setStatus(0);
        switch (callBackData) {
            case "Приют для собак":
                log.info("Выбран приют для собак");
                client.setStatus(1);
                setClientStatus(chatId, client);
                telegramBot.execute(new EditMessageText(chatId, messageId,
                        START_MESSAGE_USER_DOG.getMessage()).replyMarkup(tableService.startMenuButtonsForShelterDog()));
                log.info("Отправил пользователю меню для собак");
                break;
            case "Приют для кошек":
                log.info("Выбран приют для кошек");
                client.setStatus(2);
                setClientStatus(chatId, client);
                telegramBot.execute(new EditMessageText(chatId, messageId,
                        START_MESSAGE_USER_CAT.getMessage()).replyMarkup(tableService.startMenuButtonsForShelterCat()));
                log.info("Отправил пользователю меню для кошек");
                break;
            case  "Волонтер приюта для собак":
                log.info("Выбран приют для собак");
                client.setStatus(3);
                setClientStatus(chatId, client);
                telegramBot.execute(new EditMessageText(chatId, messageId,
                        BUTTON_VOLUNTEER_FOR_DOG_SHELTER.getMessage()).replyMarkup(tableService.volunteerFunctionality()));
                log.info("Отправил пользователю меню для собак");
                break;
            case "Волонтер приюта для кошек":
                log.info("Выбран приют для кошек");
                client.setStatus(4);
                setClientStatus(chatId, client);
                telegramBot.execute(new EditMessageText(chatId, messageId,
                        BUTTON_VOLUNTEER_FOR_CAT_SHELTER.getMessage()).replyMarkup(tableService.volunteerFunctionality()));
                log.info("Отправил пользователю меню для кошек");
                break;
        }
    }



    private void checkButtonVolunteerStatus(Update update) {
        log.info("Проверка выбора волонтера - checkButtonVolunteerStatus -statusChecker");
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        int messageId = update.callbackQuery().message().messageId();
        long chatId = update.callbackQuery().message().chat().id();
        Volunteer volunteer = new Volunteer();

        volunteer.setChatId(chatId);
        volunteer.setStatus(0);

        switch (callBackData) {
            case "Волонтер приюта для кошек":
                log.info("Выбран приют для кошек");
                volunteer.setStatus(2);
                setVolunteerStatus(chatId, volunteer);
                telegramBot.execute(new EditMessageText(chatId, messageId,
                        BUTTON_VOLUNTEER_FOR_CAT_SHELTER.getMessage()).replyMarkup(tableService.volunteerFunctionality()));
                log.info("Отправил волонтеру меню ");
                break;
            case "Волонтер приюта для собак":
                log.info("Выбран приют для собак");
                volunteer.setStatus(1);
                setVolunteerStatus(chatId, volunteer);
                telegramBot.execute(new EditMessageText(chatId, messageId,
                        BUTTON_VOLUNTEER_FOR_DOG_SHELTER.getMessage()).replyMarkup(tableService.volunteerFunctionality()));
                log.info("Отправил волонтеру меню");
                break;
        }
    }







    public void checkStartAnswers(Update update) {
        log.info("Методы проверки стартового меню - statusChecker");
        checkButtonAnswerForStatus(update);
//        checkButtonShelterStatus(update);
    }

    public void shelterStatus(Update update) {
        log.info("shelterStatus - statusChecker");
        checkButtonShelterStatus(update);
        checkButtonVolunteerStatus(update);
    }
    public void volunteerStatus(Update update) {
        log.info("volunteerStatus - statusChecker");
        checkButtonVolunteerStatus(update);
    }
    private void setClientStatus(Long chatId, Client client) {
        log.info("setClientStatus - statusChecker");
        if (clientService.findClient(chatId) == null) {
            clientService.saveClient(client);
        } else {
            clientService.updateClient(client);
        }
    }

    private void setVolunteerStatus(Long chatId, Volunteer volunteer) {
        log.info("setVolunteerStatus - statusChecker");
        if (volunteerService.findVolunteer(chatId) == null) {
            volunteerService.saveVolunteer(volunteer);
        } else {
            volunteerService.updateVolunteer(volunteer);
        }
    }
}


