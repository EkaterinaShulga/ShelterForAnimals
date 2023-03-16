package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.User;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.UserRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.UserService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.skypro.shelterforanimals.constants.BotButtonForShelterMenuEnum.*;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final  String USER_TEXT_PATTERN = "([\\W+]+)(\\s)([0-9]{11})(\\s)([\\W+]+)";

    private final TelegramBot telegramBot;
    static private final Pattern pattern = Pattern.compile(USER_TEXT_PATTERN);
    private final VolunteerRepository volunteerRepository;



    /**
     * метод проверяет сообщение от пользователя на соответствие шаблону и преобразует его для внесения
     * в БД  путем вызова метода репозитория {@link UserRepository#save(Object)} <br>
     * если сообщение было введено пользователем не верно - бот вернет ответ - что данные не сохранились, так
     * как сообщение не соответсвует шаблону
     * так же метод проверяет есть ли уже такой контакт в БД путем вызова метода репозитория <br>
     * {@link UserRepository #findAllUsersByChatId(long)} , <br>
     * если контакт ранее уже был внесен в БД - бот сообщает об этом пользователю  <br>
     * @param update
     */

    @Override
    public void createUser(Update update){
        log.info("Создание усыновителя");
        String text = update.message().text();
        long chatId = update.message().chat().id();
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            String petName = matcher.group(1);
            String phone = matcher.group(3);
            String name = matcher.group(5);
            User user = new User();
            user.setNumberPhone(phone);
            user.setUserName(name);
            user.setChatId(chatId);
            user.setPetName(petName);

            Volunteer volunteer = volunteerRepository.findByChatId(chatId);
            int status = volunteer.getStatus();

            List<User> userFromBase = userRepository.findAllUsersByChatId(chatId);
            log.info(String.valueOf(userFromBase));
            try{
                if(userFromBase.isEmpty()){
                    if(status == 1){
                        user.setStatus(status);
                        userRepository.save(user);
                        log.info("в базу занесен усыновитель собаки");
                    }
                    else if(status == 2){
                        user.setStatus(status);
                        userRepository.save(user);
                        log.info("в базу занесен усыновитель кота");
                    }
                    telegramBot.execute(new SendMessage(chatId, SAVE_INFORMATION.getMessage()));
                    log.info("Данные сохранены в базе данных");
                } else{
                    log.info("такой пользователь уже есть в базе данных");
                    telegramBot.execute(new SendMessage(chatId, "Такой пользователь уже есть в базе данных"));
                }
            }
            catch(NullPointerException e){
                log.info("пользователь не предоставил информацию о себе"+e);
            }
        }
        else {
            telegramBot.execute(new SendMessage(chatId, BOT_ANSWER_NOT_SAVED_INFO.getText()));
            log.warn(BOT_ANSWER_NOT_SAVED_INFO_LOG.getText());
        }

    }
    //  @Override
    //  public User createUser(Update update) {
    //      logger.info("Creating new User");
    //     return userRepository.save(getStringUser(update));
    //  }
    @Override
    public User getUser(Long chatId) {
        return userRepository.findByChatId(chatId);
    }


}

