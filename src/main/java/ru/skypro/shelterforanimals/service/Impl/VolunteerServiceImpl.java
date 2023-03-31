package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Record;
import ru.skypro.shelterforanimals.entity.User;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.RecordRepository;
import ru.skypro.shelterforanimals.repository.UserRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.VolunteerService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;
import static ru.skypro.shelterforanimals.constants.BotMessageVolunteer.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {
    private final UserRepository userRepository;

    private final TelegramBot telegramBot;
    private final VolunteerRepository volunteerRepository;

    private final RecordRepository recordRepository;

    @Override
    public void saveVolunteer(Volunteer volunteer) {
        log.info("saveVolunteer - volunteerServiceImpl");
        volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer findVolunteer(Long chatId) {
        log.info("findVolunteer - volunteerServiceImpl");
        return volunteerRepository.findByChatId(chatId);
    }

    @Override
    public Volunteer updateVolunteer(Volunteer volunteer) {
        log.info("updateVolunteer -  volunteerServiceImpl");
        Volunteer volunteerUpdate = volunteerRepository.findByChatId(volunteer.getChatId());
        volunteerUpdate.setStatus(volunteer.getStatus());
        volunteerUpdate.setChatId(volunteerUpdate.getChatId());

        return volunteerRepository.save(volunteerUpdate);

    }

    /**
     * the method sends a notification to the user <br>
     * via the bot that the user has passed the probation period
     *
     * @param update - update from the bot
     */

    @Override
    public void sendMessageForUserProbationPeriodIsOver(Update update) {
        long chatIdVolunteer = update.message().chat().id();
        log.info("create message for adopter");
        String info = update.message().text();
        int lengthInfo = info.length();
        String text = info.substring(7, lengthInfo);
        int chatIdUser = Integer.parseInt(text);
        User user = userRepository.findByChatId((long) chatIdUser);
        if (user != null) {
            log.info("Send massage for user - probation period is over");
            telegramBot.execute(new SendMessage(chatIdUser, "Уважаемый/ая " + user.getUserName() +
                    "\n" + ANSWER_FOR_BUTTON_PROBATION_PERIOD_IS_OVER_FOR_USER.getMessage()));
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    ANSWER_SEND_MESSAGE_PROBATION_PERIOD_IS_OVER_FOR_USER.getMessage()));
        }
        if (user == null) {
            log.info("sendMessageForUserProbationPeriodIsOver - user not found");
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    NOT_FOUND_USER.getMessage()));

        }
    }

    /**
     * the method sends a notification to the user<br>
     * via the bot that the user has not passed the probation period
     *
     * @param update - update from the bot
     */
    @Override
    public void sendMessageForUserProbationPeriodIsNotOver(Update update) {
        long chatIdVolunteer = update.message().chat().id();
        log.info("create message for adopter - probation period is not over");
        String info = update.message().text();
        int lengthInfo = info.length();
        String text = info.substring(14, lengthInfo);
        int chatIdUser = Integer.parseInt(text);
        User user = userRepository.findByChatId((long) chatIdUser);

        if (user != null) {
            log.info("Send massage for user - probation period is not over");
            telegramBot.execute(new SendMessage(chatIdUser, "Уважаемый/ая " + user.getUserName() +
                    "\n" + ANSWER_FOR_BUTTON_PROBATION_PERIOD_NOT_IS_OVER.getMessage()));
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    ANSWER_SEND_MESSAGE_PROBATION_PERIOD_IS_NOT_OVER_FOR_USER.getMessage()));
        }
        if (user == null) {
            log.info("sendMessageForUserProbationPeriodIsNotOver - user not found");
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    NOT_FOUND_USER.getMessage()));
        }
    }

    /**
     * the method sends a notification to the user<br>
     * via the bot that the user has been assigned<br>
     * an additional trial period
     *
     * @param update - update from the bot
     */

    @Override
    public void sendMessageForUserAboutExtraTime(Update update) {
        long chatIdVolunteer = update.message().chat().id();
        log.info("create message for adopter - extra time");
        String info = update.message().text();
        int lengthInfo = info.length();
        String text = info.substring(10, lengthInfo);
        int chatIdUser = Integer.parseInt(text);
        User user = userRepository.findByChatId((long) chatIdUser);
        if (user != null) {
            log.info("Send massage for user about extra time");
            telegramBot.execute(new SendMessage(chatIdUser, "Уважаемый/ая " + user.getUserName() +
                    "\n" + ANSWER_FOR_BUTTON_EXTRA_TIME_FOR_USER.getMessage()));
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    ANSWER_SEND_MESSAGE_ABOUT_EXTRA_TIME_FOR_USER.getMessage()));
        }
        if (user == null) {
            log.info("sendMessageForUserAboutExtraTime - user not found");
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    NOT_FOUND_USER.getMessage()));
        }
    }

    /**
     * the method sends a notification to the user<br>
     * via the bot that the user is not making reports well
     *
     * @param update - update from the bot
     */
    @Override
    public void sendMessageForUserAboutBadReport(Update update) {
        long chatIdVolunteer = update.message().chat().id();
        log.info("create message for adopter - bad report");
        String info = update.message().text();
        int lengthInfo = info.length();
        String text = info.substring(10, lengthInfo);
        int chatIdUser = Integer.parseInt(text);
        User user = userRepository.findByChatId((long) chatIdUser);
        if (user != null) {
            log.info("Send massage for user about bad report");
            telegramBot.execute(new SendMessage(chatIdUser, MESSAGE_FOR_ADOPTER.getMessage()));
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    ANSWER_SEND_MESSAGE_ABOUT_BAD_REPORT.getMessage()));
        }
        if (user == null) {
            log.info("sendMessageForUserAboutBadReport - user not found");
            telegramBot.execute(new SendMessage(chatIdVolunteer,
                    NOT_FOUND_USER.getMessage()));
        }
    }

    /**
     * the method checks (once every two days) the availability<br>
     * of the adopter's reports, if two days in a row the reports<br>
     * were not provided - the bot sends a notification<br>
     * about this to the volunteer
     */

    //   @Scheduled(cron = "0 0/1 * * * *") //проверка отчетов происходит один раз в сутки
    public void checkRecordAndSendReminderForVolunteer() {
        log.info("checkRecordAndSendReminderForVolunteer");
        List<Volunteer> allVolunteers = volunteerRepository.findAll();//все волонтеры
        List<User> allUsers = userRepository.findAll();//все усыновители
        LocalDate dateNow = LocalDate.now();//дата проверки отчета
        for (User user : allUsers) {
            long chatIdUser = user.getChatId();//берем чат айди усыновителя
            int statusUser = user.getStatus();//берем статус усыновителя
            List<Record> records = recordRepository.findByChatId(chatIdUser);//выгружает все отчеты по данному  chatId и потом нужный ищет по дате
            if (records != null) {//если отчеты есть
                for (Record record : records) {

                    LocalDate dateOfRecord = record.getDate();//дата отчета
                    LocalDate dateTimeOneDayBefore = LocalDate.now().minusDays(1);
                    LocalDate dateNextRecord = dateNow.minusDays(1);//предыдущий за проверяемым день

                    for (Volunteer volunteer : allVolunteers) {
                        long chatIdVolunteer = volunteer.getChatId();
                        int statusVolunteer = volunteer.getStatus();
                        if (statusVolunteer == statusUser && !dateOfRecord.equals(dateNow)) {//если с текущей датой  и следующей за ней отчета нет - бот отпавляет напоминание волонтеру
                            Record record1 = recordRepository.findRecordByChatIdAndDate(chatIdUser, dateTimeOneDayBefore);
                            if (record1 == null) {
                                telegramBot.execute(new SendMessage(chatIdVolunteer, REPORT_REMEMBER_FOR_VOLUNTEER.getMessage() + dateNow + " и " + dateNextRecord + " , " + chatIdUser));
                                log.info("Волонтеру направлено уведомление о том, что усыновитель не присылал отчет два дня подряд");
                            }
                        }
                    }

                }


            }
        }
    }
}


