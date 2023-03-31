package ru.skypro.shelterforanimals.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Record;
import ru.skypro.shelterforanimals.handler.button_answers.VolunteerButtonAnswers;
import ru.skypro.shelterforanimals.service.*;
import ru.skypro.shelterforanimals.constants.BotMessageEnum;
import ru.skypro.shelterforanimals.handler.button_answers.StatusChecker;
import ru.skypro.shelterforanimals.repository.RecordRepository;
import ru.skypro.shelterforanimals.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;

import static ru.skypro.shelterforanimals.constants.BotButtonEnum.*;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageHandlerClient {
    private final TelegramBot telegramBot;
    private final ContactService contactService;
    private final RecordService recordService;
    private final PetPhotoService petPhotoService;
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final DataCheck dataCheck;
    private final StatusChecker statusChecker;
    private final UserService userService;
    private final VolunteerButtonAnswers volunteerButtonAnswers;

    /**
     * processing information from the cat shelter menu
     * @param update
     */
    public void checkMessageFromCatClient(Update update) {
        if (update.callbackQuery() != null) {
            log.info("CallBackQuery processing for CatClient - MessageHandlerClient ");
            dataCheck.checkCatButtonAnswer(update);
            if (update.callbackQuery().data().equals("photo") ||
                    update.callbackQuery().data().equals("record")) {
                sendResponseForThirdMenu(update);
            } else if (update.callbackQuery().data().equals(BUTTON_CHECK_CONTACT.getMessage())) {
                contactService.getAllContactsForVolunteer(update);
            }  else if (update.callbackQuery().data().equals(BUTTON_VOLUNTEER_CHECK_REPORTS.getMessage())) {
                recordService.getRecordsForVolunteer(update);
                petPhotoService.getPetPhotoForVolunteer(update);
            } else if (update.callbackQuery().data().equals(BUTTON_ADD_USER.getMessage())||
                    update.callbackQuery().data().equals(BUTTON_VOLUNTEER_ADD_PET.getMessage())||
                    update.callbackQuery().data().equals(BUTTON_MESSAGE_FOR_USER.getMessage())||
                    update.callbackQuery().data().equals(BUTTON_PASSED_PROBATION_PERIOD.getMessage()) ||
                    update.callbackQuery().data().equals(BUTTON_DID_NOT_PASSED_PROBATION_PERIOD.getMessage()) ||
                    update.callbackQuery().data().equals(BUTTON_EXTRA_TIME.getMessage()) ||
                    update.callbackQuery().data().equals(BUTTON_BED_RECORD.getMessage())) {
                volunteerButtonAnswers.checkButtonAnswerVolunteer(update);
                ;
            }
        } else if (update.message().photo() != null) {
            log.info("Photo Upload processing");
            Long chatId = update.message().chat().id();
            Record record = recordRepository.findRecordByChatId(chatId);
            Long recordId = record.getRecordId();
            PhotoSize[] photos = update.message().photo();
            if (recordId != null) {
                try {
                    petPhotoService.uploadPhoto(recordId, photos);
                    telegramBot.execute(sendMessage(update.message().chat().id(), "Ваше фото сохранено"));
                } catch (IOException e) {
                    log.info("Error" + e);
                    telegramBot.execute(sendMessage(update.message().chat().id(), BotMessageEnum.ASK_HELP.getMessage()));
                }
            } else if (recordRepository.findAll().isEmpty()) {
                log.info("Таблица с отчетами пуста");
                if (userService.getUser(update.message().chat().id()) == null) {
                    log.info("Метод добавления фото: Нет пользователя");
                    telegramBot.execute(sendMessage(update.message().chat().id(), "Сначала необходимо отправить отчёт\n" +
                            USER_NOT_FOUND_MESSAGE));
                } else {
                    telegramBot.execute(sendMessage(update.message().chat().id(), "Сначала необходимо отправить отчёт\n" +
                            BotMessageEnum.DAILY_RECORD_INFO));
                }
            } else {
                log.info("Фото не добавлено");
            }
        }

    }
    /**
     * processing information from the dog shelter menu
     *
     * @param update
     */
    public void checkMessageFromDogClient(Update update) {
        if (update.callbackQuery() != null) {
            log.info("CallBackQuery processing for DogClient - MessageHandlerClient");
            dataCheck.checkDogButtonAnswer(update);
            if (update.callbackQuery().data().equals("photo") ||
                    update.callbackQuery().data().equals("record")) {
                sendResponseForThirdMenu(update);
            } else if (update.callbackQuery().data().equals(BUTTON_CHECK_CONTACT.getMessage())) {
                contactService.getAllContactsForVolunteer(update);
            } else if (update.callbackQuery().data().equals(BUTTON_VOLUNTEER_CHECK_REPORTS.getMessage())) {
                recordService.getRecordsForVolunteer(update);
                petPhotoService.getPetPhotoForVolunteer(update);
            } else if (update.callbackQuery().data().equals(BUTTON_ADD_USER.getMessage())||
                    update.callbackQuery().data().equals(BUTTON_PASSED_PROBATION_PERIOD.getMessage()) ||
                    update.callbackQuery().data().equals(BUTTON_DID_NOT_PASSED_PROBATION_PERIOD.getMessage()) ||
                    update.callbackQuery().data().equals(BUTTON_EXTRA_TIME.getMessage()) ||
                    update.callbackQuery().data().equals(BUTTON_BED_RECORD.getMessage())||
                    update.callbackQuery().data().equals(BUTTON_VOLUNTEER_ADD_PET.getMessage())||
                    update.callbackQuery().data().equals(BUTTON_MESSAGE_FOR_USER.getMessage())) {
                volunteerButtonAnswers.checkButtonAnswerVolunteer(update);
            }
        } else if (update.message().photo() != null) {
            log.info("Photo Upload processing");
            Long chatId = update.message().chat().id();
            Record record = recordRepository.findRecordByChatId(chatId);
            Long recordId = record.getRecordId();
            PhotoSize[] photos = update.message().photo();
            if (recordId != null) {
                try {
                    petPhotoService.uploadPhoto(recordId, photos);
                    telegramBot.execute(sendMessage(update.message().chat().id(), "Ваше фото сохранено"));
                } catch (IOException e) {
                    log.info("Error" + e);
                    telegramBot.execute(sendMessage(update.message().chat().id(), ASK_HELP.getMessage()));
                }
            } else if (recordRepository.findAll().isEmpty()) {
                log.info("Таблица с отчетами пуста");
                if (userRepository.findAllUsersByChatId(update.message().chat().id()).isEmpty()) {
                    log.info("Метод добавления фото: Нет пользователя");
                    telegramBot.execute(sendMessage(update.message().chat().id(), "Сперва необходимо отправить отчёт\n" +
                            USER_NOT_FOUND_MESSAGE));
                } else {
                    telegramBot.execute(sendMessage(update.message().chat().id(), "Сперва необходимо отправить отчёт\n" +
                            DAILY_RECORD_INFO));
                }
            } else {
                log.info("Фото не добавлено");
            }
        }
    }

    private SendMessage sendMessage(long chatId, String textToSend) {
        return new SendMessage(chatId, textToSend);
    }

    /**
     * processing of the "photo" and "report" buttons
     *
     * @param update
     */

    private void sendResponseForThirdMenu(Update update) {
        log.info(" вызван метод sendResponseForThirdMenu - MessageHandlerClient");
        String answerMenu = update.callbackQuery().data();
        LocalDate date = LocalDate.now();
        long chatId = update.callbackQuery().message().chat().id();
        int messageId = update.callbackQuery().message().messageId();
        switch (answerMenu) {
            case "photo":
                telegramBot.execute(new EditMessageText(chatId, messageId, PHOTO.getMessage()));
                log.warn("IMPORTANT" + PHOTO.getMessage());
                break;
            case "record":
                telegramBot.execute(new SendMessage(chatId,  DAILY_RECORD_INFO.getMessage()));
                log.warn("send info for client about report");
                break;
        }
    }

    public void startMessage(Update update) {
        log.info("startMessage - Проверка стартового меню - MessageHandlerClient");
        statusChecker.checkStartAnswers(update);
        statusChecker.shelterStatus(update);
        statusChecker.volunteerStatus(update);

    }

}

