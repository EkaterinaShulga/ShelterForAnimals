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

    /**
     * the method processes the response from the start menu buttons of<br>
     * the cat shelter and sends the response to the user
     *
     * @param update
     */

    public void checkButtonAnswerCats(Update update) {
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        long chatId = update.callbackQuery().message().chat().id();
        switch (callBackData) {
            case "Узнать информацию о приюте":
                String newMessage = USER_CHOOSE_SHELTER_INFO.getMessage();
                telegramBot.execute(new SendMessage(chatId, newMessage)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutShelterForCat()));
                break;
            case "Как взять кошку из приюта":
                String message = ANSWER_FOR_MENU_INFORMATION_CAT.getMessage();
                telegramBot.execute(new SendMessage(chatId, message)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutCat()));
                break;
            case "Прислать отчет о питомце":
                String creatRecordMessage = BotMessageEnum.DAILY_RECORD_INFO.getMessage();
                telegramBot.execute(new SendMessage(chatId, creatRecordMessage));
                log.info("Message Sent" + " Method - checkButtonAnswerCats");
                break;
            case "Прислать фото питомца":
                String creatPetPhotoMessage = BotMessageEnum.PHOTO.getMessage();
                telegramBot.execute(new SendMessage(chatId, creatPetPhotoMessage));
                log.info("Message Sent" + " Method - checkButtonAnswerCats");
                break;
            case "Позвать волонтера":
                telegramBot.execute(new SendMessage(chatId, ASK_HELP.getMessage()));
                break;
        }
    }

    /**
     * the method processes the response from the menu buttons<br>
     * of the cat shelter (information about the shelter and how to take the animal)<br>
     * and sends a response to the user<br>
     *
     * @param update
     */
    public void sendResponseForFirstAndSecondMenuCats(Update update) {
        log.info("вызвал метод sendResponseForFirstAndSecondMenuCats");
        String answerMenu = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        log.info("Ответ от кнопки " + answerMenu);
        switch (answerMenu) {
            case "infoCat":
                SendMessage answer = new SendMessage(chatId, INFORMATION_ABOUT_SHELTER_CAT.getMessage());
                log.warn("information about shelter");
                telegramBot.execute(answer);
                break;
            case "workTimeCat":
                SendMessage answer2 = new SendMessage(chatId, WORK_TIME_SHELTER_CAT.getMessage());
                log.warn("work time shelter");
                telegramBot.execute(answer2);
                break;
            case "addressCat":
                SendMessage answer3 = new SendMessage(chatId, ADDRESS_SHELTER_CAT.getMessage());
                log.warn("address shelter");
                telegramBot.execute(answer3);
                break;
            case "wayCat":
                SendMessage answer4 = new SendMessage(chatId, WAY_SHELTER_CAT.getMessage());
                log.warn("way shelter");
                telegramBot.execute(answer4);
                break;
            case "securityContactCat":
                SendMessage answer5 = new SendMessage(chatId, SECURITY_CONTACT_CAT.getMessage());
                log.warn("security contact cat");
                telegramBot.execute(answer5);
                break;
            case "safetyCat":
                SendMessage answer6 = new SendMessage(chatId, SAFETY_CAT.getMessage());
                log.warn("safety cat");
                telegramBot.execute(answer6);
                break;
            case "volunteerCat":
                telegramBot.execute(new SendMessage(chatId, ANSWER_THANK_FOR_CONTACTING.getMessage()));
                break;
            case "contactCat":
                telegramBot.execute(new SendMessage(chatId, COMMAND_SAFE_CONTACT_DETAILS_FOR_COMMUNICATION.getMessage()));
                break;
            case "rulesCat":
                SendMessage answer7 = new SendMessage(chatId, RULES_FOR_DATING_CAT.getMessage());
                log.warn("IMPORTANT rules cat");
                telegramBot.execute(answer7);
                break;
            case "docsCat":
                SendMessage answer8 = new SendMessage(chatId, LIST_DOCUMENTS_FOR_CAT.getMessage());
                log.warn("IMPORTANT documents for cat");
                telegramBot.execute(answer8);
                break;
            case "transportationCat":
                SendMessage answer9 = new SendMessage(chatId, RECOMMENDATIONS_FOR_TRANSPORTATION.getMessage());
                log.warn("IMPORTANT transportation cat");
                telegramBot.execute(answer9);
                break;
            case "arrangementKitty":
                SendMessage answer10 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_KITTEN.getMessage());
                log.warn("IMPORTANT arrangement kitty");
                telegramBot.execute(answer10);
                break;
            case "arrangementCat":
                SendMessage answer11 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_ADULT_CAT.getMessage());
                log.warn("IMPORTANT arrangement cat");
                telegramBot.execute(answer11);
                break;
            case "arrangementCatDisabled":
                SendMessage answer12 = new SendMessage(chatId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_DISABLED_CAT.getMessage());
                log.warn("IMPORTANT arrangement cat disabled");
                telegramBot.execute(answer12);
                break;
            case "rejectCat":
                SendMessage answer13 = new SendMessage(chatId, REASONS_FOR_REFUSAL_CAT.getMessage());
                log.warn("IMPORTANT reject cat");
                telegramBot.execute(answer13);
                break;
        }
    }

}

