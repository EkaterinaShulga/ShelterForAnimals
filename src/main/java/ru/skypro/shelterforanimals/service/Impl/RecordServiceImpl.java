package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Client;
import ru.skypro.shelterforanimals.entity.Record;
import ru.skypro.shelterforanimals.entity.User;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.ClientRepository;
import ru.skypro.shelterforanimals.repository.RecordRepository;
import ru.skypro.shelterforanimals.repository.UserRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.RecordService;

import java.time.LocalDate;
import java.util.List;

import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final TelegramBot telegramBot;
    private final ClientRepository clientRepository;
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;


    /**
     * the method searches by chatId and the current date <br>
     * in the database for record for an adopted pet, <br>
     * if it finds it, it returns the id of this record,  <br>
     * if not, it notifies the adopter through the bot that  <br>
     * in order to save the photo, you first need to add a record <br>
     *
     * @param update - update from the bot
     * @return id record or send message - first need to add record
     */
    @Override
    public Long findRecordId(Update update) {
        long chatId = update.message().chat().id();
        LocalDate dateNow = LocalDate.now();
        Record record = recordRepository.findRecordByChatIdAndDate(chatId, dateNow);
        if (record == null) {
            telegramBot.execute(new SendMessage(chatId, "Для начала отчёт"));
        } else {
            log.info("в базе есть запись искомому id и дате, фото можно сохранять");
        }
        assert record != null;
        return record.getRecordId();

    }

    /**
     * method creates and add record on database
     *
     * @param update - update from the bot
     */

    public void saveRecord(Update update) {
        log.info("Процесс сохранения отчета");
        LocalDate date = LocalDate.now();
        String record = update.message().text();
        long chatId = update.message().chat().id();
        Client client = clientRepository.findByChatId(chatId);
        int status = client.getStatus();


        int dietIndex = record.indexOf("Диета:");
        int adaptationIndex = record.indexOf("Адаптация:");
        int behaviorIndex = record.indexOf("Изменение в поведении:");

        String dietResult = record.substring(dietIndex, adaptationIndex);
        String adaptationResult = record.substring(adaptationIndex, behaviorIndex);
        String behaviorResult = record.substring(behaviorIndex, record.length());


        if (dietResult.length() < 8) {
            telegramBot.execute(new SendMessage(chatId, RECORD_DIETA.getMessage()));
        } else if (adaptationResult.length() < 8) {
            telegramBot.execute(new SendMessage(chatId, RECORD_ADAPTATION.getMessage()));
        } else if (behaviorResult.length() < 8) {
            telegramBot.execute(new SendMessage(chatId, RECORD_BEHAVIOR.getMessage()));
        } else {
            Record recordForBase = new Record();
            recordForBase.setDiet(dietResult);
            recordForBase.setAdaptation(adaptationResult);
            recordForBase.setChangeInBehavior(behaviorResult);
            recordForBase.setChatId(chatId);
            recordForBase.setDate(date);
            recordForBase.setStatus(status);
            recordRepository.save(recordForBase);
            telegramBot.execute(new SendMessage(chatId, SAVE_INFORMATION.getMessage()));
            log.info("текстовый отчет занесен в базу данных");
        }
    }

    /**
     * method finds records on database from the status volunteer
     *
     * @param update - update from the bot
     */

    @Override
    public void getRecordsForVolunteer(Update update) {
        log.info("Процесс поиска записей - getRecordsForVolunteer");
        long chatId = update.callbackQuery().message().chat().id();
        Volunteer volunteer = volunteerRepository.findByChatId(chatId);
        int status = volunteer.getStatus();
        List<Record> records = recordRepository.getRecordsByStatus(status);
        System.out.println(records.toString());
        if (records.isEmpty()) {
            telegramBot.execute(new SendMessage(chatId, NOT_FIND_RECORD.getMessage()));
            log.info("В базе не найдено записей");
        } else {
            telegramBot.execute(new SendMessage(chatId, FIND_RECORD.getMessage() + records));

            log.info("В базе найдены записи");
        }

    }

    @Override
    public Record findById(Long recordId) {
        return recordRepository.findById(recordId).orElseThrow();
    }


    /**
     * once a day, the method checks for the presence<br>
     * of records of pets (for the current day),<br>
     * which should be sent every day by adoptive parents <br>
     * who are on probation, if there is no record,<br>
     * the bot sends a reminder that <br>
     * need to send a record of the pet
     */


    //   @Scheduled(cron = "0 0/1 * * * *") //проверка отчетов происходит один раз в сутки
    public void checkRecordAndSendReminder() {
        List<Volunteer> allVolunteers = volunteerRepository.findAll();//все волонтеры
        List<User> allUsers = userRepository.findAll();//все усыновители
        LocalDate dateNow = LocalDate.now();//дата проверки отчета
        for (User user : allUsers) {
            long chatIdUser = user.getChatId();//берем чат айди усыновителя
            int status = user.getStatus();//берем статус усыновителя
            List<Record> records = recordRepository.findByChatId(chatIdUser);//выгружает все отчеты по данному  chatId и потом нужный ищет по дате
            if (records != null) {//если отчеты есть
                for (Record record : records) {
                    LocalDate dateOfRecord = record.getDate();//ищем дату каждого отчета
                    //    System.out.println(dateOfRecord + " dateOfRecord");
                    if (!dateOfRecord.equals(dateNow)) {//если с текущей датой отчета нет - бот отпавляет напоминание усыновителю
                        telegramBot.execute(new SendMessage(chatIdUser, REPORT_REMEMBER_ABOUT_RECORD.getMessage() + dateNow));
                        log.info("Пользователю направлено напоминание о необходимости прислать текстовый отчет за конкретный день");
                    }
                }
                for (Volunteer volunteer : allVolunteers) {
                    if (volunteer.getStatus() == status) {
                        telegramBot.execute(new SendMessage(volunteer.getChatId(),
                                "отправил усыновителю напоминание о том, что нужно прилать отчет за  " + dateNow));
                    }
                }
            } else {
                telegramBot.execute(new SendMessage(user.getChatId(),
                        "по данному " + chatIdUser + " в базе нет отчетов"));
                System.out.println("по данному " + chatIdUser + " в базе нет отчетов");
            }

        }
    }
}

