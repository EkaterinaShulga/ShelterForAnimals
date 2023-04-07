package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import ru.skypro.shelterforanimals.entity.Contact;
import ru.skypro.shelterforanimals.repository.ClientRepository;
import ru.skypro.shelterforanimals.repository.ContactRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.Impl.ContactServiceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {

    @Mock
    ContactRepository contactRepository;

    @InjectMocks
    ContactServiceImpl contactService;

    private Contact contact;

    private List<Contact> contacts;
    Update update;


    @BeforeEach
    public void init(){
       contact = new Contact();
       contact.setId(1);
       contact.setName("Иванов Иван Иванович");
       contact.setNumberPhone("89061875572");
       contact.setDate(LocalDate.now());
       contact.setClientStatus(1);

       contacts=new ArrayList<>();
       contacts.add(contact);
    }

    @Test
    public void saveContact(){
        verify(contactRepository, atMostOnce()).save(contact);
        assertNotNull(contact);
    }

    @Test
    public void findContactById(){
        when(contactRepository.findById(anyInt())).thenReturn(Optional.of(contact));
        Optional<Contact> result = contactService.findContactById(contact.getId());
        assertNotNull(result);
    }

    @Test
    public void saveContactTest() throws IOException {
        TelegramBot tgBot = Mockito.mock(TelegramBot.class);
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        VolunteerRepository volunteerRepository = Mockito.mock(VolunteerRepository.class);
        ContactServiceImpl contactService = new ContactServiceImpl(
                contactRepository,
                tgBot,
                clientRepository,
                volunteerRepository

        );

        Contact contact = new Contact();
        contactService = Mockito.spy(contactService);


        String info = replacedJson2("89061877772 Иванов Иван Иванович ");
        Update update = BotUtils.parseUpdate(info);
        //Client client = clientRepository.findByChatId(update.message().chat().id());
        contactRepository.save(contact);
        verify(tgBot).execute(new SendMessage(update.message().chat().id(), BOT_ANSWER_NOT_SAVED_CONTACT.getMessage()));
    }

    public String replacedJson2(String replacement) throws IOException {
        String json = FileUtils.readFileToString(ResourceUtils.getFile("classpath:updates/update_4.json"),
                StandardCharsets.UTF_8);
        return json.replace("%data%", replacement);
    }
}
