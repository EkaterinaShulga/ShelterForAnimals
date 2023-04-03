package ru.skypro.shelterforanimals.handler.button_answers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.constants.BotMessageEnum;
import ru.skypro.shelterforanimals.service.TableService;


import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;
import static ru.skypro.shelterforanimals.constants.TextForResponseFromMenuButtonsCat.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatButtonAnswers {

    private final TelegramBot telegramBot;
    private final TableService tableService;

    static public final String INFO_ABOUT_SHELTER = "Информация о приюте";
    static public final String INFO_ABOUT_SHELTER_CAT = "Информация о приюте для кошек";
    static public final String HOW_GET_CAT_OUT_OF_SHELTER = "Как взять кошку из приюта";
    static public final String SEND_PET_REPORT = "Прислать отчет о питомце";
    static public final String SEND_PET_PHOTO = "Прислать фото питомца";
    static public final String HELP = "Позвать волонтера";
    static public final String CALL_VOLUNTEER = "Связаться с волонтером";
    static public final String WORK_SCHEDULE_SHELTER = "Время работы приюта";
    static public final String ADDRESS_SHELTER = "Адрес приюта";
    static public final String DRIVING_DIRECTIONS = "Схема проезда";
    static public final String SECURITY_CAT = "Как получить пропуск";
    static public final String SAFETY_SHELTER = "Правила поведения в приюте";
    static public final String LEAVE_DATA_FOR_COMMUNICATION = "Оставить данные для связи";
    static public final String MEET_PET = "Правила знакомства с животным";
    static public final String DOC = "Список необходимых документов";
    static public final String TRANSPORTATION = "Как перевозить питомца";
    static public final String KITTY = "Дом.условия для котенка";
    static public final String ARRANGEMENT_CAT = "Дом.условия для кошки";
    static public final String ARRANGEMENT_INVALID = "Условия для животного-инвалида";
    static public final String REJECT = "Спиоск причин отказа";


    /**
     * the method processes the response from the start menu buttons of<br>
     * the cat shelter and sends the response to the user
     *
     * @param update- update from the bot
     */

    public void checkButtonAnswerCats(Update update) {
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        long chatId = update.callbackQuery().message().chat().id();
        switch (callBackData) {
            case INFO_ABOUT_SHELTER:
                String newMessage = USER_CHOOSE_SHELTER_INFO.getMessage();
                telegramBot.execute(new SendMessage(chatId, newMessage)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutShelterForCat()));
                break;
            case HOW_GET_CAT_OUT_OF_SHELTER:
                String message = ANSWER_FOR_MENU_INFORMATION_CAT.getMessage();
                telegramBot.execute(new SendMessage(chatId, message)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutCat()));
                break;
            case SEND_PET_REPORT:
                String creatRecordMessage = BotMessageEnum.DAILY_RECORD_INFO.getMessage();
                telegramBot.execute(new SendMessage(chatId, creatRecordMessage));
                log.info("Message Sent" + " Method - checkButtonAnswerCats");
                break;
            case SEND_PET_PHOTO:
                String creatPetPhotoMessage = BotMessageEnum.PHOTO.getMessage();
                telegramBot.execute(new SendMessage(chatId, creatPetPhotoMessage));
                log.info("Message Sent" + " Method - checkButtonAnswerCats");
                break;
            case HELP:
                telegramBot.execute(new SendMessage(chatId, ASK_HELP.getMessage()));
                break;
        }
    }

    /**
     * the method processes the response from the menu buttons<br>
     * of the cat shelter (information about the shelter and how to take the animal)<br>
     * and sends a response to the user<br>
     *
     * @param update- update from the bot
     */
    public void sendResponseForFirstAndSecondMenuCats(Update update) {
        log.info("вызвал метод sendResponseForFirstAndSecondMenuCats");
        String answerMenu = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        log.info("Ответ от кнопки " + answerMenu);
        switch (answerMenu) {
            case INFO_ABOUT_SHELTER_CAT:
                SendMessage answer = new SendMessage(chatId, INFORMATION_ABOUT_SHELTER_CAT.getMessage());
                log.warn("information about shelter");
                telegramBot.execute(answer);
                break;
            case WORK_SCHEDULE_SHELTER:
                SendMessage answer2 = new SendMessage(chatId, WORK_TIME_SHELTER_CAT.getMessage());
                log.warn("work time shelter");
                telegramBot.execute(answer2);
                break;
            case ADDRESS_SHELTER:
                SendMessage answer3 = new SendMessage(chatId, ADDRESS_SHELTER_CAT.getMessage());
                log.warn("address shelter");
                telegramBot.execute(answer3);
                break;
            case DRIVING_DIRECTIONS:
                SendMessage answer4 = new SendMessage(chatId, WAY_SHELTER_CAT.getMessage());
                log.warn("way shelter");
                telegramBot.execute(answer4);
                break;
            case SECURITY_CAT:
                SendMessage answer5 = new SendMessage(chatId, SECURITY_CONTACT_CAT.getMessage());
                log.warn("security contact cat");
                telegramBot.execute(answer5);
                break;
            case SAFETY_SHELTER:
                SendMessage answer6 = new SendMessage(chatId, SAFETY_CAT.getMessage());
                log.warn("safety cat");
                telegramBot.execute(answer6);
                break;
            case CALL_VOLUNTEER:
                telegramBot.execute(new SendMessage(chatId, ANSWER_THANK_FOR_CONTACTING.getMessage()));
                break;
            case LEAVE_DATA_FOR_COMMUNICATION:
                telegramBot.execute(new SendMessage(chatId, COMMAND_SAFE_CONTACT_DETAILS_FOR_COMMUNICATION.getMessage()));
                break;
            case MEET_PET:
                SendMessage answer7 = new SendMessage(chatId, RULES_FOR_DATING_CAT.getMessage());
                log.warn("IMPORTANT rules cat");
                telegramBot.execute(answer7);
                break;
            case DOC:
                SendMessage answer8 = new SendMessage(chatId, LIST_DOCUMENTS_FOR_CAT.getMessage());
                log.warn("IMPORTANT documents for cat");
                telegramBot.execute(answer8);
                break;
            case TRANSPORTATION:
                SendMessage answer9 = new SendMessage(chatId, RECOMMENDATIONS_FOR_TRANSPORTATION.getMessage());
                log.warn("IMPORTANT transportation cat");
                telegramBot.execute(answer9);
                break;
            case KITTY:
                SendMessage answer10 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_KITTEN.getMessage());
                log.warn("IMPORTANT arrangement kitty");
                telegramBot.execute(answer10);
                break;
            case ARRANGEMENT_CAT:
                SendMessage answer11 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_ADULT_CAT.getMessage());
                log.warn("IMPORTANT arrangement cat");
                telegramBot.execute(answer11);
                break;
            case ARRANGEMENT_INVALID:
                SendMessage answer12 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_DISABLED_CAT.getMessage());
                log.warn("IMPORTANT arrangement cat disabled");
                telegramBot.execute(answer12);
                break;
            case REJECT:
                SendMessage answer13 = new SendMessage(chatId, REASONS_FOR_REFUSAL_CAT.getMessage());
                log.warn("IMPORTANT reject cat");
                telegramBot.execute(answer13);
                break;
        }
    }

}

