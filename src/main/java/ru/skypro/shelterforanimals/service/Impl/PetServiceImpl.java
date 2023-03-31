package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Cat;
import ru.skypro.shelterforanimals.entity.Dog;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.CatRepository;
import ru.skypro.shelterforanimals.repository.DogRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.PetPhotoService;
import ru.skypro.shelterforanimals.service.PetService;
import ru.skypro.shelterforanimals.service.RecordService;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static ru.skypro.shelterforanimals.constants.BotMessageEnum.BOT_ANSWER_NOT_SAVED_INFO_LOG;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.SAVE_INFORMATION;
import static ru.skypro.shelterforanimals.constants.BotMessageVolunteer.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {
    private static final String PET_TEXT_PATTERN = "([\\W+]+)(\\s)([\\W+]+)(\\s)([\\W+]+)(\\s)([\\W+]+)";

    static private final Pattern pattern = Pattern.compile(PET_TEXT_PATTERN);
    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private final VolunteerRepository volunteerRepository;
    private final TelegramBot telegramBot;

    /**
     * method creates and save pet on the database<br>
     * depending on the status of the volunteer,<br>
     * a cat or a dog is saved to the database
     *
     * @param update - update from the bot
     */
    @Override
    public void savePet(Update update) {
        log.info("Сохранение питомца в базе данных");
        String text = update.message().text();
        long chatId = update.message().chat().id();
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            String name = matcher.group(1);
            String color = matcher.group(3);
            String age = matcher.group(5) + " " + matcher.group(7);
            Dog dogFromDataBase = dogRepository.findDogByName(name);
            Cat catFromBase = catRepository.findCatByName(name);
            Volunteer volunteer = volunteerRepository.findByChatId(chatId);
            int status = volunteer.getStatus();
            log.info("volunteer status" + status);
            if (dogFromDataBase == null) {
                if (status == 1) {
                    saveDog(name, color, age, chatId);
                    telegramBot.execute(new SendMessage(chatId, SAVE_INFORMATION.getMessage()));
                    log.info("Данные сохранены в базе данных");
                }
            } else {
                log.info("такой питомец уже есть в базе данных");
                telegramBot.execute(new SendMessage(chatId, ANSWER_FOR_BUTTON_SAVE_PET_CAN_NOT_SAVE.getMessage()));
            }

            if (catFromBase == null) {
                if (status == 2) {
                    saveCat(name, color, age, chatId);
                    telegramBot.execute(new SendMessage(chatId, SAVE_INFORMATION.getMessage()));
                    log.info("Данные сохранены в базе данных");
                }
            } else {
                log.info("такой питомец уже есть в базе данных");
                telegramBot.execute(new SendMessage(chatId, ANSWER_FOR_BUTTON_SAVE_PET_CAN_NOT_SAVE.getMessage()));
            }

        } else if (!matcher.matches()) {
            telegramBot.execute(new SendMessage(chatId, ANSWER_FOR_BUTTON_SAVE_PET_ERROR_IN_TEMPLATE.getMessage()));
            log.info(BOT_ANSWER_NOT_SAVED_INFO_LOG.getMessage());
        }
    }

    /**
     * * method creates and save dog
     *
     * @param name   - name dog
     * @param color  - color dog
     * @param age    -age dog
     * @param chatId - chatId volunteer, who adds dog
     */

    @Override
    public void saveDog(String name, String color, String age, Long chatId) {
        log.info("saveDog  - petServiceImpl");
        Dog dog = new Dog();
        dog.setAge(age);
        dog.setName(name);
        dog.setColor(color);
        dog.setChatId(chatId);
        dogRepository.save(dog);
    }

    /**
     * * method creates and save cat
     *
     * @param name   - name cat
     * @param color  - color cat
     * @param age    -age cat
     * @param chatId - chatId volunteer, who adds cat
     */
    @Override
    public void saveCat(String name, String color, String age, Long chatId) {
        log.info("saveCat - petServiceImpl");
        Cat cat = new Cat();
        cat.setAge(age);
        cat.setName(name);
        cat.setColor(color);
        cat.setChatId(chatId);
        catRepository.save(cat);
    }
}

