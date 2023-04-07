package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import ru.skypro.shelterforanimals.service.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.START_MESSAGE;


@ExtendWith(MockitoExtension.class)
public class TelegramBotUpdateListenerTest {
    @Mock
    TelegramBot telegramBot;

    @Mock
    PetService petService;
    @InjectMocks
    TelegramBotUpdatesListener telegramBotUpdatesListener;

    private String replacedJson2(String replacement) throws IOException {
        String json = FileUtils.readFileToString(ResourceUtils.getFile("classpath:updates/update_start.json"),
                StandardCharsets.UTF_8);
        return json.replace("%data%", replacement);
    }

    @Test
    public void sendMessage() throws IOException {
        String info = replacedJson2("/start");
        Update update = BotUtils.parseUpdate(info);
        String username = "@" + update.message().chat().username() + START_MESSAGE.getMessage();
        verify(telegramBot, atMostOnce()).execute(new SendMessage(update.message().chat().id(),username)
                .replyMarkup(any()));
        List<Update> allUpdates = new ArrayList<>();
        allUpdates.add(update);
        telegramBotUpdatesListener.process(allUpdates);


    }

    private String replacedJson3(String replacement) throws IOException {
        String json = FileUtils.readFileToString(ResourceUtils.getFile("classpath:updates/update_create_pet.json"),
                StandardCharsets.UTF_8);
        return json.replace("%data%", replacement);
    }

    @Test
    public void sendMessage2() throws IOException {

        String info = replacedJson3("Шарик 89061855572 Иванов Иван Иванович");
        Update update = BotUtils.parseUpdate(info);
        List<Update> allUpdates = new ArrayList<>();
        allUpdates.add(update);

        verify(petService, atMostOnce()).savePet(update);
        telegramBotUpdatesListener.process(allUpdates);



    }
}
