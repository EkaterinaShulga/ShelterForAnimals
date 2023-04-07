package ru.skypro.shelterforanimals.handler;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import ru.skypro.shelterforanimals.service.ContactService;
import ru.skypro.shelterforanimals.service.Impl.TelegramBotUpdatesListener;
import ru.skypro.shelterforanimals.service.PetPhotoService;
import ru.skypro.shelterforanimals.service.RecordService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static ru.skypro.shelterforanimals.constants.BotButtonEnum.BUTTON_CHECK_CONTACT;
import static ru.skypro.shelterforanimals.constants.BotButtonEnum.BUTTON_VOLUNTEER_CHECK_REPORTS;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerClientTest {

    @Mock
    DataCheck dataCheck;
    @Mock
    TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Mock
    ContactService contactService;

    @Mock
    RecordService recordService;

    @Mock
    PetPhotoService petPhotoService;
    @InjectMocks
    MessageHandlerClient messageHandlerClient;



    private String replacedJson(String replacement) throws IOException {
        String json = FileUtils.readFileToString(ResourceUtils.getFile("classpath:updates/update_test.json"),
                StandardCharsets.UTF_8);
        return json.replace("%data%", replacement);
    }
    @Test
    public void checkMessageFromCatClientTest() throws IOException {
        String info = replacedJson(BUTTON_CHECK_CONTACT.getMessage());
        Update update = BotUtils.parseUpdate(info);
        verify(telegramBotUpdatesListener, atMostOnce()).messageHandler(update);
        contactService.getAllContactsForVolunteer(update);
        messageHandlerClient.checkMessageFromCatClient(update);

    }
    @Test
    public void checkMessageFromCatClientTest2() throws IOException {
        String info = replacedJson(BUTTON_VOLUNTEER_CHECK_REPORTS.getMessage());
        Update update = BotUtils.parseUpdate(info);
        verify(telegramBotUpdatesListener, atMostOnce()).messageHandler(update);
        recordService.getRecordsForVolunteer(update);
        petPhotoService.getPetPhotoForVolunteer(update);
        messageHandlerClient.checkMessageFromCatClient(update);

    }
}
