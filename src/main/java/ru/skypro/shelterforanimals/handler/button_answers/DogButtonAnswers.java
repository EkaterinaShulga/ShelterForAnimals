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
import static ru.skypro.shelterforanimals.constants.TextForResponseFromMenuButtonsDog.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class DogButtonAnswers {
    private final TelegramBot telegramBot;
    private final TableService tableService;

    private final static String INFORMATION_ABOUT_SHELTER_DOG = "Узнать информацию о приюте";
    private final static String INSTRUCTION_DOG = "Как взять собаку из приюта";
    private final static String SEND_PET_REPORT = "Прислать отчет о питомце";
    private final static String SEND_PET_PHOTO = "Прислать фото питомца";
    private final static String HELP = "Позвать волонтера";
    private final static String INFORMATION_ABOUT_SHELTER = "Информация о приюте";
    private final static String WORK_SCHEDULE_SHELTER = "Время работы приюта";
    private final static String ADDRESS_SHELTER = "Адрес приюта";
    private final static String DRIVING_DIRECTIONS = "Схема проезда";
    private final static String SAFETY_SHELTER = "Правила поведения в приюте";

    private final static String LEAVE_DATA_FOR_COMMUNICATION = "Оставить данные для связи";
    private final static String CALL_VOLUNTEER = "Связаться с волонтером";
    private final static String MEET_PET = "Правила знакомства с животным";
    static public final String DOC = "Список необходимых документов";
    static public final String TRANSPORTATION = "Как перевозить питомца";
    static public final String PUPPY = "Дом.условия для щенка";
    static public final String ARRANGEMENT_INVALID = "Условия для животного-инвалида";
    static public final String ARRANGEMENT_DOG = "Дом.условия для собаки";
    static public final String CYNOLOGIST = "Рекомендации от кинологов";
    static public final String GOOD_CYNOLOGIST = "Хорошие кинологи";
    static public final String REJECT = "Спиоск причин отказа";

    /**
     * the method processes the response from the start menu buttons of<br>
     * the dog shelter and sends the response to the user
     *
     * @param update- update from the bot
     */

    public void checkButtonAnswerDogs(Update update) {
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        long chatId = update.callbackQuery().message().chat().id();
        switch (callBackData) {
            case INFORMATION_ABOUT_SHELTER_DOG:
                String newMessage = USER_CHOOSE_SHELTER_INFO.getMessage();
                telegramBot.execute(new SendMessage(chatId, newMessage)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutShelterForDog()));
                break;
            case INSTRUCTION_DOG:
                String message = USER_CHOOSE_DOG_INSTRUCTION.getMessage();
                telegramBot.execute(new SendMessage(chatId, message)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutGod()));
                break;
            case SEND_PET_REPORT:
                String creatRecordMessage = BotMessageEnum.DAILY_RECORD_INFO.getMessage();
                telegramBot.execute(new SendMessage(chatId, creatRecordMessage));
                log.info("Message Sent" + " Method - sendRecord");
                break;
            case SEND_PET_PHOTO:
                String creatPetPhotoMessage = BotMessageEnum.PHOTO.getMessage();
                telegramBot.execute(new SendMessage(chatId, creatPetPhotoMessage));
                log.info("Message Sent" + " Method -  checkButtonAnswerDogs");
                break;
            case HELP:
                telegramBot.execute(new SendMessage(chatId, ANSWER_THANK_FOR_CONTACTING.getMessage()));
                break;
        }
    }

    /**
     * the method processes the response from the menu buttons<br>
     * of the dog shelter (information about the shelter and how to take the animal)<br>
     * and sends a response to the user<br>
     *
     * @param update- update from the bot
     */
    public void sendResponseForFirstAndSecondMenuDogs(Update update) {
        log.info("вызвал метод sendAnswer");
        String answerMenu = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        log.info("Ответ от кнопки " + answerMenu);
        switch (answerMenu) {
            case INFORMATION_ABOUT_SHELTER:
                SendMessage answer = new SendMessage(chatId, INFORMATION_SHELTER_DOG.getMessage());
                log.warn("IMPORTANT information about shelter dog");
                telegramBot.execute(answer);
                break;
            case WORK_SCHEDULE_SHELTER:
                SendMessage answer2 = new SendMessage(chatId, WORK_TIME_SHELTER_DOG.getMessage());
                log.warn("IMPORTANT work time shelter dog");
                telegramBot.execute(answer2);
                break;
            case ADDRESS_SHELTER:
                SendMessage answer3 = new SendMessage(chatId, ADDRESS_SHELTER_DOG.getMessage());
                log.warn("IMPORTANT  address shelter dog");
                telegramBot.execute(answer3);
                break;
            case DRIVING_DIRECTIONS:
                SendMessage answer4 = new SendMessage(chatId, WAY_SHELTER_DOG.getMessage());
                log.warn("IMPORTANT way shelter");
                telegramBot.execute(answer4);
                break;
            case SAFETY_SHELTER:
                SendMessage answer5 = new SendMessage(chatId, SAFETY_DOG.getMessage());
                log.warn("IMPORTANT safety shelter");
                telegramBot.execute(answer5);
                break;
            case CALL_VOLUNTEER:
                telegramBot.execute(new SendMessage(chatId, ASK_HELP.getMessage()));
                break;
            case LEAVE_DATA_FOR_COMMUNICATION:
                telegramBot.execute(new SendMessage(chatId, COMMAND_SAFE_CONTACT_DETAILS_FOR_COMMUNICATION.getMessage()));
                break;
            case MEET_PET:
                SendMessage answer6 = new SendMessage(chatId, RULES_FOR_DATING_DOG.getMessage());
                log.warn("IMPORTANT rules");
                telegramBot.execute(answer6);
                break;
            case DOC:
                SendMessage answer7 = new SendMessage(chatId, LIST_DOCUMENTS_FOR_DOG.getMessage());
                log.warn("IMPORTANT about documents");
                telegramBot.execute(answer7);
                break;
            case TRANSPORTATION:
                SendMessage answer8 = new SendMessage(chatId, RECOMMENDATIONS_FOR_TRANSPORTATION_DOG.getMessage());
                log.warn("IMPORTANT transportation dogs");
                telegramBot.execute(answer8);
                break;
            case PUPPY:
                SendMessage answer9 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_PUPPY.getMessage());
                log.warn("IMPORTANT arrangement puppy");
                telegramBot.execute(answer9);
                break;
            case ARRANGEMENT_DOG:
                SendMessage answer10 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_ADULT_DOG.getMessage());
                log.warn("IMPORTANT arrangement dog");
                telegramBot.execute(answer10);
                break;
            case ARRANGEMENT_INVALID:
                SendMessage answer11 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_DISABLED_DOG.getMessage());
                log.warn("IMPORTANT arrangement dog invalid");
                telegramBot.execute(answer11);
                break;
            case CYNOLOGIST:
                SendMessage answer12 = new SendMessage(chatId, RECOMMENDATIONS_OF_DOG_CYNOLOGISTS.getMessage());
                log.warn("IMPORTANT cynologist");
                telegramBot.execute(answer12);
                break;
            case GOOD_CYNOLOGIST:
                SendMessage answer13 = new SendMessage(chatId, GOOD_CYNOLOGISTS.getMessage());
                log.warn("IMPORTANT good cynologists");
                telegramBot.execute(answer13);
                break;
            case REJECT:
                SendMessage answer14 = new SendMessage(chatId, REJECTS_DOGS.getMessage());
                log.warn("IMPORTANT  reject");
                telegramBot.execute(answer14);
                break;
        }
    }
}
