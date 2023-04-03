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


    private final static String ADD_USER = "Добавить усыновителя";
    private final static String MESSAGE_FOR_USER = "Сообщение усыновителю";
    private final static String VOLUNTEER_ADD_PET = "Добавить питомца";
    private final static String PASSED_PROBATION_PERIOD = "ИС пройден";
    private final static String DID_NOT_PASSED_PROBATION_PERIOD ="ИС не пройден";
    private final static String EXTRA_TIME = "Доп время";
    private final static String BED_RECORD = "Плохой отчет";


    /**
     * the method processes the response from the menu buttons<br>
     * of the volunteer (its functionality) <br>
     * and sends a response to the user/volunteer<br>
     *
     * @param update- update from the bot
     */

    public void checkButtonAnswerVolunteer(Update update) {
        String callBackData = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        log.info("Ответ от кнопки " + callBackData);
        switch (callBackData) {
            case ADD_USER:
                SendMessage message = new SendMessage(chatId, ANSWER_FOR_BUTTON_SAVE_USER.getMessage());
                log.warn("send information for volunteer - how create new user");
                telegramBot.execute(message);
                break;
            case VOLUNTEER_ADD_PET:
                SendMessage message1 = new SendMessage(chatId, ANSWER_FOR_BUTTON_SAVE_PET.getMessage());
                log.warn("send information for volunteer - how create new pet");
                telegramBot.execute(message1);
                break;
            case MESSAGE_FOR_USER:
                String message3 = ANSWER_FOR_BUTTON_PROBATION_PERIOD.getMessage();
                telegramBot.execute(new SendMessage(chatId, message3)
                        .replyMarkup(tableService.makeButtonsMessagesToUserAboutProbationPeriod()));
                log.info("send information for volunteer - probation period");
                break;
            case PASSED_PROBATION_PERIOD:
                telegramBot.execute(new SendMessage(chatId,
                        ANSWER_FOR_BUTTON_PROBATION_PERIOD_SEND_CHAT_ID.getMessage()));
                break;
            case DID_NOT_PASSED_PROBATION_PERIOD:
                telegramBot.execute(new SendMessage(chatId,
                        ANSWER_FOR_BUTTON_PROBATION_PERIOD_NOT_IS_OVER_SEND_CHAT_ID.getMessage()));
                break;
            case EXTRA_TIME:
                telegramBot.execute(new SendMessage(chatId, ANSWER_FOR_BUTTON_EXTRA_TIME.getMessage()));
                break;

            case BED_RECORD:
                telegramBot.execute(new SendMessage(chatId, ANSWER_FOR_BUTTON_BAD_REPORT.getMessage()));
                break;
        }

    }

}

