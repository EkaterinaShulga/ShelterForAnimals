package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Client;
import ru.skypro.shelterforanimals.entity.Contact;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.ClientRepository;
import ru.skypro.shelterforanimals.repository.ContactRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.ContactService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.skypro.shelterforanimals.constants.BotButtonForShelterMenuEnum.BOT_ANSWER_NOT_SAVED_CONTACT;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
    static private final Pattern pattern2 = Pattern.compile(TelegramBotUpdatesListener.CONTACT_TEXT_PATTERN);
    private  final ContactRepository contactRepository;
    private final TelegramBot telegramBot;
    private final ClientRepository clientRepository;

    private final VolunteerRepository volunteerRepository;



    /**
     * save the contact in the database
     * @param update
     *
     */
    @Override
    public void saveContact(Update update){
        log.info("Сохранение контакта");
        String text = update.message().text();
        long chatId = update.message().chat().id();
        LocalDateTime localDateTime =  LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Client client = clientRepository.findByChatId(chatId);
        Matcher matcher = pattern2.matcher(text);
        if (matcher.matches()) {
            String phone = matcher.group(1);
            String name = matcher.group(3);
            Contact contact = new Contact();
            contact.setNumberPhone(phone);
            contact.setName(name);
            contact.setDateTime(localDateTime);
            contact.setClient(client);
            List<Contact> contacts = contactRepository.findContactByNumberPhoneAndName(phone,name);
            log.info(String.valueOf(contacts));

            try{
                if(contacts.isEmpty()){
                    if(client.getStatus()==1){
                        contact.setClientStatus(client.getStatus());
                        contactRepository.save(contact);
                    }
                    if(client.getStatus()==2) {
                        contact.setClientStatus(client.getStatus());
                        contactRepository.save(contact);
                    }
                    telegramBot.execute(new SendMessage(chatId, SAVE_INFORMATION_CONTACT.getMessage()));
                    log.info("Данные сохранены в базе данных");
                } else {
                    log.info("такой контакт уже есть в базе данных");
                    telegramBot.execute(new SendMessage(chatId, HAVE_CONTACT.getMessage()));
                }
            }
            catch(NullPointerException e){
                log.info("пользователь предоставил новый контакт");
            }
        }
        else if(!matcher.matches()){
            telegramBot.execute(new SendMessage(chatId, BOT_ANSWER_NOT_SAVED_CONTACT.getText()));
            log.warn("Не смогу сохранить запись. Введенное сообщение не соотствует шаблону");
        }
    }


    @Override
    public void getAllContactsForVolunteer(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                DELETE_ALL_CONTACTS.getMessage()));
        Volunteer volunteer = volunteerRepository.findByChatId(update.callbackQuery().message().chat().id());
        List<Contact> allContacts = contactRepository.findAll();
        List<Contact> allContactsForVolunteer = new ArrayList<>();
        for(Contact contact:allContacts){
            if(contact.getClientStatus() == volunteer.getStatus()){
                allContactsForVolunteer.add(contact);
            }
        }
        if (allContactsForVolunteer.isEmpty()) {
            telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                    NOT_FIND_CONTACTS_FOR_VOLUNTEER.getMessage()));
        }
        else {
            telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                    FIND_CONTACTS_FOR_VOLUNTEER.getMessage() + allContactsForVolunteer));
        }
    }




    @Override
    public void deleteAllContacts(Update update) {
        Volunteer volunteer = volunteerRepository.findByChatId(update.message().chat().id());
        int statusClient = volunteer.getStatus();
        List<Contact> allContacts = contactRepository.findContactsByClientStatus(statusClient);
        for(Contact contact: allContacts){
            contactRepository.delete(contact);
        }
        List<Contact> allContactsAfterDelete = contactRepository.findContactsByClientStatus(statusClient);
        if (allContactsAfterDelete.isEmpty()) {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    DONE.getMessage()));
        }
    }


    public Optional<Contact> findContactById(int id){
        log.warn("check the correctness of the faculty id");
        return contactRepository.findById(id);
    }

    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }
    public Contact addContact(Contact contact){
        return contactRepository.save(contact);
    }


}

