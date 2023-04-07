package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.shelterforanimals.constants.BotMessageEnum;
import ru.skypro.shelterforanimals.entity.Contact;
import ru.skypro.shelterforanimals.entity.User;
import ru.skypro.shelterforanimals.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.skypro.shelterforanimals.service.Impl.TestUtils.getFileContent;

@SpringBootTest
@ActiveProfiles("test")
public class BaseIntegrationTest {

    @MockBean
    private TelegramBot telegramBot;

    private final Update helloUpdate = BotUtils.parseUpdate(getFileContent("classpath:updates/update_1.json"));
    private final Update responseAddingContact = BotUtils.parseUpdate(getFileContent("classpath:updates/update_2.json"));
    private final Update createRecordAnswer = BotUtils.parseUpdate(getFileContent("classpath:updates/update_3.json"));
    private final Update userCreationUpdate = BotUtils.parseUpdate(getFileContent("classpath:updates/update_create_user.json"));
    @Autowired
    private TelegramBotUpdatesListener telegramBotUpdatesListener;
    @Autowired
    private UserRepository userRepository;

    private Contact contact = new Contact(1,1300060749,"Иванов Иван Иванович", "89061875572", LocalDate.now(), 2);

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void testHelloMessage() {
        telegramBotUpdatesListener.process(List.of(helloUpdate));

        ArgumentCaptor<SendMessage> sentMessage = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(sentMessage.capture());

        assertEquals(helloUpdate.message().chat().id(), sentMessage.getValue().getParameters().get("chat_id"));
        assertEquals("@" + helloUpdate.message().chat().username() + BotMessageEnum.START_MESSAGE.getMessage()
                , sentMessage.getValue().getParameters().get("text"));
    }

    @Test
    public void testUserSaveContact() {
        telegramBotUpdatesListener.process(List.of(responseAddingContact));
        ArgumentCaptor<SendMessage> sentMessage = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(sentMessage.capture());

        assertEquals(responseAddingContact.message().chat().id(), sentMessage.getValue().getParameters().get("chat_id"));
        assertEquals(BotMessageEnum.CAN_NOT_SAVE_CONTACT.getMessage(), sentMessage.getValue().getParameters().get("text"));

    }

    @Test
    public void testSendRecordMessage() {
        telegramBotUpdatesListener.process(List.of(createRecordAnswer));
        ArgumentCaptor<SendMessage> sentMessage = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(sentMessage.capture());

        assertEquals(createRecordAnswer.message().chat().id(), sentMessage.getValue().getParameters().get("chat_id"));
        assertEquals(BotMessageEnum.USER_NOT_FOUND.getMessage()
                , sentMessage.getValue().getParameters().get("text"));
    }

    @Test
    public void testUserCreation() {
        String[] splittedMessage = userCreationUpdate.message().text().split("\\s");
        String pet = splittedMessage[0];
        String phone = splittedMessage[1];
        String username = splittedMessage[2] + " " + splittedMessage[3] + " " + splittedMessage[4];

        telegramBotUpdatesListener.process(List.of(userCreationUpdate));

        User createdUser = userRepository.findByChatId(userCreationUpdate.message().chat().id());
        assertEquals(createdUser.getUserId(), createdUser.getUserId());
        assertEquals(username, createdUser.getUserName());
        assertEquals(phone, createdUser.getNumberPhone());
        assertEquals(pet, createdUser.getPetName());
        assertEquals(createdUser.getChatId(), createdUser.getChatId());
        assertEquals(createdUser.getStatus(), createdUser.getStatus());
    }


}
