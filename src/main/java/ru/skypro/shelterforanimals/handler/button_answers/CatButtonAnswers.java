package ru.skypro.shelterforanimals.handler.button_answers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.constants.BotMessageEnum;
import ru.skypro.shelterforanimals.service.TableService;

import static ru.skypro.shelterforanimals.constants.BotButtonForShelterMenuEnum.*;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;
import static ru.skypro.shelterforanimals.constants.TextForResponseFromMenuButtonsCat.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatButtonAnswers {

    private final TelegramBot telegramBot;
    private final TableService tableService;

    public void checkButtonAnswerCats(Update update) {
        String callBackData = update.callbackQuery().data();
        log.info(callBackData);
        long chatId = update.callbackQuery().message().chat().id();
        switch (callBackData) {
            case "Узнать информацию о приюте":
                //вызов  меню этапа 1
                String newMessage = USER_CHOOSE_SHELTER_INFO.getMessage();
                //  EditMessageText messageText = new EditMessageText(chatId, messageId, newMessage);
                telegramBot.execute(new SendMessage(chatId, newMessage)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutShelterForCat()));
                break;
            case "Как взять кошку из приюта":
                //вызов  меню этапа 2
                String message = ANSWER_FOR_MENU_INFORMATION_CAT.getMessage();
                telegramBot.execute(new SendMessage(chatId, message)
                        .replyMarkup(tableService.menuButtonsWithInformationAboutCat()));
                break;
            case "Прислать отчет о питомце":
                //Отчет по питомцу
                String creatRecordMessage = BotMessageEnum.DAILY_RECORD_INFO.getMessage();
                telegramBot.execute(new SendMessage(chatId, creatRecordMessage));
                log.info("Message Sent" + " Method - checkButtonAnswerCats");
                break;
            case "Прислать фото питомца": //
                //фото питомца
                String creatPetPhotoMessage = BotMessageEnum.PHOTO.getMessage();//изменить запись
                telegramBot.execute(new SendMessage(chatId, creatPetPhotoMessage));
                log.info("Message Sent" + " Method - checkButtonAnswerCats");
                break;
            case "Позвать волонтера":
                telegramBot.execute(new SendMessage(chatId, ASK_HELP.getMessage()));
                break;
        }
    }

    public void sendResponseForFirstAndSecondMenuCats(Update update) {
        log.info("вызвал метод sendResponseForFirstAndSecondMenuCats");
        String answerMenu = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        int messageId = update.callbackQuery().message().messageId();
        log.info("Ответ от кнопки " + answerMenu);
        switch (answerMenu) {
            case "infoCat":
                EditMessageText answer = new EditMessageText(chatId, messageId, INFORMATION_ABOUT_SHELTER_CAT.getMessage());
                log.warn("information about shelter");
                telegramBot.execute(answer);
                break;
            case "workTimeCat":
                EditMessageText answer2 = new EditMessageText(chatId, messageId, WORK_TIME_SHELTER_CAT.getMessage());
                log.warn("work time shelter");
                telegramBot.execute(answer2);
                break;
            case "addressCat":
                EditMessageText answer3 = new EditMessageText(chatId, messageId, ADDRESS_SHELTER_CAT.getMessage());
                log.warn("address shelter");
                telegramBot.execute(answer3);
                break;
            case "wayCat":
                EditMessageText answer4 = new EditMessageText(chatId, messageId, WAY_SHELTER_CAT.getMessage());
                log.warn("way shelter");
                telegramBot.execute(answer4);
                break;
            case "securityContactCat":
                EditMessageText answer5 = new EditMessageText(chatId, messageId, SECURITY_CONTACT_CAT.getMessage());
                log.warn("security contact cat");
                telegramBot.execute(answer5);
                break;
            case "safetyCat":
                EditMessageText answer6 = new EditMessageText(chatId, messageId, SAFETY_CAT.getMessage());
                log.warn("safety cat");
                telegramBot.execute(answer6);
                break;
            case "volunteerCat":
                telegramBot.execute(new EditMessageText(chatId, messageId, "Спасибо за обращение, волонтер приюта свяжется " +
                        "с Вами"));
                break;
            case "contactCat":
                telegramBot.execute(new EditMessageText(chatId, messageId, COMMAND_SAFE_CONTACT_DETAILS_FOR_COMMUNICATION.getText()));
                break;
            case "rulesCat": //второе меню - как взять кошку из приюта
                EditMessageText answer7 = new EditMessageText(chatId, messageId, RULES_FOR_DATING_CAT.getMessage());
                log.warn("IMPORTANT rules cat");
                telegramBot.execute(answer7);
                break;
            case "docsCat":
                EditMessageText answer8 = new EditMessageText(chatId, messageId, LIST_DOCUMENTS_FOR_CAT.getMessage());
                log.warn("IMPORTANT documents for cat");
                telegramBot.execute(answer8);
                break;
            case "transportationCat":
                EditMessageText answer9 = new EditMessageText(chatId, messageId, RECOMMENDATIONS_FOR_TRANSPORTATION.getMessage());
                log.warn("IMPORTANT transportation cat");
                telegramBot.execute(answer9);
                log.info("Кнопка 3 работает");
                break;
            case "arrangementKitty":
                EditMessageText answer10 = new EditMessageText(chatId, messageId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_KITTEN.getMessage());
                log.warn("IMPORTANT arrangement kitty");
                telegramBot.execute(answer10);
                break;
            case "arrangementCat":
                EditMessageText answer11 = new EditMessageText(chatId, messageId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_ADULT_CAT.getMessage());
                log.warn("IMPORTANT arrangement cat");
                telegramBot.execute(answer11);
                break;
            case "arrangementCatDisabled":
                EditMessageText answer12 = new EditMessageText(chatId, messageId, RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_DISABLED_CAT.getMessage());
                log.warn("IMPORTANT arrangement cat disabled");
                telegramBot.execute(answer12);
                break;
            case "rejectCat":
                EditMessageText answer13 = new EditMessageText(chatId, messageId, REASONS_FOR_REFUSAL_CAT.getMessage());
                log.warn("IMPORTANT reject cat");
                telegramBot.execute(answer13);
                break;
        }
    }

}

