package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.ContactRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.VolunteerService;

import static ru.skypro.shelterforanimals.constants.BotMessageVolunteer.MESSAGE_FOR_ADOPTER;


@Slf4j
@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final TelegramBot telegramBot;
    private final VolunteerRepository volunteerRepository;

    private final ContactRepository contactRepository;

    public VolunteerServiceImpl(TelegramBot telegramBot, VolunteerRepository volunteerRepository,
                                ContactRepository contactRepository) {
        this.telegramBot = telegramBot;
        this.volunteerRepository = volunteerRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public void saveVolunteer(Volunteer volunteer) {
        log.info("метод saveVolunteer - сохраняем волонтера - volunteerServiceImpl");
        volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer findVolunteer(Long chatId) {
        log.info("метод findVolunteer - ищем волонтера в бд-volunteerServiceImpl");
        return volunteerRepository.findByChatId(chatId);
    }

    @Override
    public Volunteer updateVolunteer(Volunteer volunteer) {
        log.info("метод updateVolunteer -  volunteerServiceImpl");
        Volunteer volunteerUpdate = volunteerRepository.findByChatId(volunteer.getChatId());
        volunteerUpdate.setStatus(volunteer.getStatus());
        volunteerUpdate.setChatId(volunteerUpdate.getChatId());

        return volunteerRepository.save(volunteerUpdate);

    }

    @Override
    public void sendMessageForAdopter(long chatId) {
        telegramBot.execute(new SendMessage(chatId,  MESSAGE_FOR_ADOPTER.getMessage()));
    }


}


