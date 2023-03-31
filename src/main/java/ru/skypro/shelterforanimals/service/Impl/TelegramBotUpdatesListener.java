package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.skypro.shelterforanimals.handler.MessageHandlerClient;
import ru.skypro.shelterforanimals.service.*;
import org.springframework.stereotype.Service;


import ru.skypro.shelterforanimals.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final UserRepository userRepository;

    private final TelegramBot telegramBot;
    private final UserServiceImpl userService;

    private final RecordServiceImpl recordService;

    private final VolunteerServiceImpl volunteerService;

    private final ContactServiceImpl contactService;

    private final ClientService clientService;
    private final PetService petService;
    private final PetPhotoService petPhotoService;

    private final MessageHandlerClient messageHandlerClient;
    private final TableService tableService;

    static public final String CONTACT_TEXT_PATTERN = "([0-9]{11})(\\s)([\\W+]+)";

    private static final String USER_TEXT_PATTERN = "([\\W+]+)(\\s)([0-9]{11})(\\s)([\\W+]+)";
    private static final String PET_TEXT_PATTERN = "([\\W+]+)(\\s)([\\W+]+)(\\s)([\\W+]+)(\\s)([\\W+]+)";

    @PostConstruct
    public void init() throws IOException {
        String json = Files.readString(Paths.get("update.json"));
        Update update = BotUtils.parseUpdate(json);
        telegramBot.setUpdatesListener(this);
    }


    /**
     * the method processes all incoming messages from the bot,<br>
     * if the message is entered correctly, it passes <br>
     * it to the {@code messageHandler(update)} method, if not, <br>
     * the bot returns a message that the information was entered incorrectly
     *
     * @param updates - update from the bot
     * @return - int
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            try {
                messageHandler(update);
            } catch (Exception e) {
                log.info("Информация введена не корректно: " + e);
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Внимание! Информация введена не кооректно"));
            }
        });
        log.info("вернул ответ" + UpdatesListener.CONFIRMED_UPDATES_ALL);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * if the callbackQuery is not empty, the method processes it passes in <br>
     * {@code messageHandlerClient.startMessage(update)} <br>
     * if client not null - the method determines the status <br>
     * of the client and then calls the corresponding update processing methods<br>
     * otherwise it calls the method {@code  checkAnswer(update)}
     *
     * @param update - update from the bot
     */
    public void messageHandler(Update update) throws IOException {
        log.info("Processing messageHandler");
        if (update.callbackQuery() != null) {
            Long chatId = update.callbackQuery().message().chat().id();
            log.info("messageHandler - case: callbackQuery - telegramBotUpdateListener");
            messageHandlerClient.startMessage(update);
            if (clientService.findClient(chatId) != null) {
                if (clientService.findClient(chatId).getStatus() == 1L) {
                    log.info("messageHandler - Dogs Client");
                    messageHandlerClient.checkMessageFromDogClient(update);
                } else if (clientService.findClient(chatId).getStatus() == 2L) {
                    log.info("messageHandler - Cats Client");
                    messageHandlerClient.checkMessageFromCatClient(update);
                } else if (volunteerService.findVolunteer(chatId).getStatus() == 1L) {
                    messageHandlerClient.checkMessageFromDogClient(update);
                    log.info("messageHandler -  dogVolunteer");
                } else if (volunteerService.findVolunteer(chatId).getStatus() == 2L) {
                    messageHandlerClient.checkMessageFromCatClient(update);
                    log.info("messageHandler -  catVolunteer");
                }
            }
        } else {
            checkAnswer(update);
        }
    }


    /**
     * the method processes the /start command <br>
     * and other updates coming from the bot <br>
     * in the form of text messages or photo<br>
     * <p>
     * if the text of the message entered by the user<br>
     * is not correct or something went wrong, the bot will
     * offer to contact the volunteer<br>
     *
     * @param update - update from the bot
     * @throws IOException - if some problem with update
     */
    public void checkAnswer(Update update) throws IOException {
        log.info("Processing checkAnswer:");
        Long chatId = update.message().chat().id();
        String inputText = update.message().text();
        PhotoSize[] photos = update.message().photo();
        if (inputText != null) {
            if (inputText.equals("/start")) {
                sendGreetingMessageStatus(update);
            } else if (inputText.matches(USER_TEXT_PATTERN)) {
                userService.createUser(update);
            } else if (inputText.matches(PET_TEXT_PATTERN)) {
                petService.savePet(update);
                log.info("save pet");
            } else if (inputText.contains("chatOk")) {
                log.info("volunteer send chatId of user - chatOk");
                volunteerService.sendMessageForUserProbationPeriodIsOver(update);
            } else if (inputText.contains("chatNotIsOver")) {
                log.info("volunteer send chatId of user - chatNotIsOver");
                volunteerService.sendMessageForUserProbationPeriodIsNotOver(update);
            } else if (inputText.contains("extraTime")) {
                log.info("volunteer send chatId of user - extraTime");
                volunteerService.sendMessageForUserAboutExtraTime(update);
            } else if (inputText.contains("badReport")) {
                log.info("volunteer send chatId of user - badReport");
                volunteerService.sendMessageForUserAboutBadReport(update);
            } else if (inputText.matches(CONTACT_TEXT_PATTERN)) {
                log.info("Processing checkAnswer, case: create contact:");
                if (clientService.findClient(chatId) != null) {
                    contactService.saveContact(update);
                    log.info("save contact");
                } else {
                    log.info("User не внесён в БД - users");
                    telegramBot.execute(sendMessage(chatId, USER_NOT_FOUND_MESSAGE.getMessage()));
                }
            } else if (inputText.contains("Отчёт за")) {
                log.info("Вызвал метод для сохранения текстового отчета");
                if (userRepository.findAllUsersByChatId(chatId).isEmpty()) {
                    log.info("Контакта нет в списке");
                    String needUser = USER_NOT_FOUND.getMessage();
                    telegramBot.execute(sendMessage(chatId, needUser));
                } else {
                    recordService.saveRecord(update);
                }
            } else if (inputText.contains(DELETE_COMMAND.getMessage()))
                contactService.deleteAllContacts(update);
        } else if (photos != null) {
            Long recordId = recordService.findRecordId(update);
            if (recordId != null) {
                petPhotoService.uploadPhoto(recordId, update.message().photo());
                log.info("Сохранил фото");
            }
        } else {
            telegramBot.execute(sendMessage(chatId, ASK_HELP.getMessage()));
        }
    }

    /**
     * the method returns a welcome message and<br>
     * a start menu to the user (user/volunteer)
     *
     * @param update - update from the bot
     */
    public void sendGreetingMessageStatus(Update update) {
        try {
            String greetingMessage = "@" + update.message().chat().username() + START_MESSAGE.getMessage();
            telegramBot.execute(new SendMessage(update.message().chat().id(), greetingMessage)
                    .replyMarkup(tableService.userStatusMenuButtons()));
            log.info("Отправил меню для определения статуса входа");
        } catch (NullPointerException e) {
            log.info("Вернулся не корректный ответ");
        }
    }


    /**
     * create new sendMessage
     *
     * @param chatId     - chatId
     * @param textToSend - text message
     * @return - SendMessage
     */
    private SendMessage sendMessage(long chatId, String textToSend) {
        return new SendMessage(chatId, textToSend);
    }


}





