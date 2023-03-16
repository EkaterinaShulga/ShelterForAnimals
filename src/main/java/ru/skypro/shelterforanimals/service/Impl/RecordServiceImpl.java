package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Client;
import ru.skypro.shelterforanimals.entity.Record;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.ClientRepository;
import ru.skypro.shelterforanimals.repository.RecordRepository;
import ru.skypro.shelterforanimals.repository.ReportRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.RecordService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private final ReportRepository reportRepository;


    @Override
    public Long findRecordId(Update update) {
        Long chatId = update.message().chat().id();
        LinkedList<Record> recordsList = recordRepository.findAllRecordByChatId(chatId);
        Record record = recordsList.peekLast();
        if (record != null) {
            return record.getRecordId();
        } else if (recordRepository.findAll().isEmpty()) {
            telegramBot.execute(new SendMessage(chatId, "Для начала отчёт"));
        } else {
            return recordRepository.findRecordByChatId(chatId).getRecordId();
        }
        return null;
    }

    /**
     * метод сохраняет в базе данных часть отчета отвечающую за информацию  <br>
     * об измеении в поведении питомца <br>
     * see ChangeInBehavior
     *
     * @param update
     */

    public void saveRecord(Update update) {
        log.info("Процесс сохранения отчета");
        LocalDateTime localDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
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
            recordForBase.setDateTime(localDate);
            recordForBase.setStatus(status);
            recordRepository.save(recordForBase);
            telegramBot.execute(new SendMessage(chatId, SAVE_INFORMATION.getMessage()));
            log.info("текстовый отчет занесен в базу данных");
        }
    }

    @Override
    public void getRecordsForVolunteer(Update update) {
        log.info("Процесс поиска записей - getRecordsForVolunteer");
        Volunteer volunteer = volunteerRepository.findByChatId(update.callbackQuery().message().chat().id());
        int status = volunteer.getStatus();
        telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                "Записи об усыновленных питомцах"));
        List<Record> allRecords = recordRepository.findAll();
        List<Record> records = new ArrayList<>();
        for (Record record : allRecords) {
            if (record.getStatus() == status) {
                records.add(record);
            }
        }
        if (records.isEmpty()) {
            telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                    NOT_FIND_RECORD.getMessage()));
            log.info("В базе не найдено записей");
        } else {
            telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                    FIND_RECORD.getMessage() + records));

            log.info("В базе найдены записи");
        }

    }


}

