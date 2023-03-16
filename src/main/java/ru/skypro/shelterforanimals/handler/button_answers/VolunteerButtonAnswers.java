package ru.skypro.shelterforanimals.handler.button_answers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.service.RecordService;
import ru.skypro.shelterforanimals.service.VolunteerService;

import static ru.skypro.shelterforanimals.constants.BotMessageVolunteer.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerButtonAnswers {
    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final RecordService recordService;


    public void checkButtonAnswerVolunteer(Update update) {
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        long chatId = update.callbackQuery().message().chat().id();
        log.info("Ответ от кнопки " + callBackData);
        switch (callBackData) {
            case " Добавить усыновителя ":
                SendMessage message = new SendMessage(chatId, ANSWER_FOR_BUTTON_SAVE_USER.getMessage());
                log.warn("send information for volunteer - how create new user");
                telegramBot.execute(message);
                break;
            case " Добавить питомца ":
                SendMessage message1 = new SendMessage(chatId, ANSWER_FOR_BUTTON_SAVE_PET.getMessage());
                log.warn("send information for volunteer - how create new pet");
                telegramBot.execute(message1);
                break;
           /* case " Проверить отчеты ":
                SendMessage message2 = new SendMessage(chatId, ANSWER_FOR_BUTTON_CHECK_REPORTS.getMessage());
                logger.warn("send information for volunteer - reports");
                telegramBot.execute(message2);
                break;*/
        }
    }

}

