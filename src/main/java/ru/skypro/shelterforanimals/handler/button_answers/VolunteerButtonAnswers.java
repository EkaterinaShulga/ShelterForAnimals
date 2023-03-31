package ru.skypro.shelterforanimals.handler.button_answers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.service.TableService;


import static ru.skypro.shelterforanimals.constants.BotMessageVolunteer.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerButtonAnswers {
    private final TelegramBot telegramBot;
    private final TableService tableService;


    /**
     * the method processes the response from the menu buttons<br>
     * of the volunteer (its functionality) <br>
     * and sends a response to the user/volunteer<br>
     *
     * @param update
     */

    public void checkButtonAnswerVolunteer(Update update) {
        String callBackData = update.callbackQuery().data();
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
            case " Сообщение усыновителю ":
                String message3 = ANSWER_FOR_BUTTON_PROBATION_PERIOD.getMessage();
               telegramBot.execute(new SendMessage(chatId, message3)
                               .replyMarkup(tableService.makeButtonsMessagesToUserAboutProbationPeriod()));
                log.info("send information for volunteer - probation period");
                break;
            case "ИС пройден":
               telegramBot.execute(new SendMessage(chatId,
                       ANSWER_FOR_BUTTON_PROBATION_PERIOD_SEND_CHAT_ID.getMessage()));
                break;
            case "ИС не пройден":
                telegramBot.execute(new SendMessage(chatId,
                        ANSWER_FOR_BUTTON_PROBATION_PERIOD_NOT_IS_OVER_SEND_CHAT_ID.getMessage()));
                break;
            case "Доп время":
                telegramBot.execute(new SendMessage(chatId, ANSWER_FOR_BUTTON_EXTRA_TIME.getMessage()));
                break;

            case"Плохой отчет":
                telegramBot.execute(new SendMessage(chatId, ANSWER_FOR_BUTTON_BAD_REPORT.getMessage()));
                break;
        }

    }

}

