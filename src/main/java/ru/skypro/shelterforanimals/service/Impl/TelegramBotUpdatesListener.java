package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import ru.skypro.shelterforanimals.handler.MessageHandlerClient;
import ru.skypro.shelterforanimals.service.*;
import ru.skypro.shelterforanimals.entity.Record;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Contact;


import ru.skypro.shelterforanimals.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;


import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;

    private final TelegramBot telegramBot;
    private final UserServiceImpl userService;

    private final RecordServiceImpl recordService;


    private final VolunteerServiceImpl volunteerService;

    private final ContactServiceImpl contactService;


    private final ClientService clientService;
    private final PetService petService;
    private final PetPhotoService petPhotoService;
    private final ReportService reportService;

    private final MessageHandlerClient messageHandlerClient;
    private final TableService tableService;

    static public final String CONTACT_TEXT_PATTERN = "([0-9]{11})(\\s)([\\W+]+)";

    private static final String USER_TEXT_PATTERN = "([\\W+]+)(\\s)([0-9]{11})(\\s)([\\W+]+)";
    private static final String PET_TEXT_PATTERN = "([\\W+]+)(\\s)([\\W+]+)(\\s)([\\W+]+)(\\s)([\\W+]+)";
    static private final String exampleContact = "89993334466 Иванов Иван Иванович";

    @PostConstruct
    public void init() throws IOException {
        String json = Files.readString(Paths.get("update.json"));
        Update update = BotUtils.parseUpdate(json);
        telegramBot.setUpdatesListener(this);
    }

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
     * если пользователь решил начать общение, метод обрабатывает команду /start и вызывает метод<br>
     * {@code sendGreetingMessage(update)} <br>
     *
     * @param update
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

    public void checkAnswer(Update update) throws IOException {
        log.info("Processing checkAnswer:");
        Long chatId = update.message().chat().id();
        String inputText = update.message().text();


       /* Record record = recordRepository.findRecordByChatId(chatId);
        long recordId = record.getRecordId();
        PhotoSize[] photos = update.message().photo();*/

        if (inputText != null) {
            if (inputText.equals("/start")) {
                sendGreetingMessageStatus(update);
                //   } else if (inputText.equals("/createuser")) {
                //       telegramBot.execute(sendMessage(chatId, BotMessageEnum.CREATE_USER_INFO.getMessage()));
            } else if (inputText.matches(USER_TEXT_PATTERN)) {
                //telegramBot.execute(sendMessage(chatId, BotMessageEnum.CREATE_USER_INFO.getMessage()));
                userService.createUser(update);
            } else if (inputText.matches(PET_TEXT_PATTERN)) {
                petService.savePet(update);
                log.info("save pet");

            } else if (inputText.matches(CONTACT_TEXT_PATTERN)) {
                log.info("Processing checkAnswer, case: create contact:");
                //    if (userRepository.findAllUsersByChatId(chatId).isEmpty()) {
                if (clientService.findClient(chatId) != null) {
                    contactService.saveContact(update);
                    // userService.createUser(update);
                    log.info("save contact");
                } else {
                    log.info("User не внесён в БД - users");
                    telegramBot.execute(sendMessage(chatId, USER_NOT_FOUND_MESSAGE.getMessage()));
                }
                //    } else if (inputText.matches("photo")) {
                //     } else if ( inputText.matches ("/photo")) {
            } else if (update.message().photo() != null && update.message().text() == null ) {

                petPhotoService.uploadPhoto(chatId, update.message().photo());
                System.out.println(chatId + " id");
                // logger.info("Процесс сохранения фото питомца присланного усыновителем");
                //  messageHendlerClient.checkMessageFromCatClient(update);
                // petPhotoService.uploadPhoto(recordId, photos);
                //petPhotoService.uploadPhoto(recordId, photos);
                log.info("Сохранил фото");
                // String messageForRecords = PHOTO.getMessage();
                // telegramBot.execute(sendMessage(chatId, messageForRecords));
            } else if (inputText.contains("Отчёт за")) {
                log.info("Вызвал метод для сохранения текстового отчета");
                if (userRepository.findAllUsersByChatId(chatId).isEmpty()) {
                    log.info("Контакта нет в списке");
                   // String needUser = USER_NOT_FOUND_MESSAGE.getMessage();
                    String needUser = USER_NOT_FOUND.getMessage();
                    telegramBot.execute(sendMessage(chatId, needUser));
                } else {
                    //recordService.saveRecord(update);
                    reportService.saveReport(update);
                }
            } else if (inputText.contains(DELETE_COMMAND.getMessage()))
                contactService.deleteAllContacts(update);
        } else {
            telegramBot.execute(sendMessage(chatId, ASK_HELP.getMessage()));
        }
    }

    /**
     * метод возвращает пользователю приветственное сообшение и <br>
     * кнопки основного меню <br>
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


    private SendMessage sendMessage(long chatId, String textToSend) {
        return new SendMessage(chatId, textToSend);
    }


    /**
     * метод отправляет пользователю список разделов меню с кратким описанием сути каждого раздела <br>
     * за каждый раздел меню отвечает соотствующая константа
     *
     * @param
     * @see Contact
     */


    @Scheduled(cron = "0 0/1 * /1")
    public void checkAvailabilityOfReportAndSendReminder(long chatId, LocalDateTime dateTime) {
        List<Record> records = recordRepository.findRecordsByChatIdAndDateTime(chatId, dateTime);
        if (records.isEmpty()) {
            telegramBot.execute(new SendMessage(chatId, REPORT_REMEMBER.getMessage() + dateTime.toString()));
            log.info("Пользователю направлено напоминание");
        }
    }

}





