package ru.skypro.shelterforanimals.service.Impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.PetPhoto;
import ru.skypro.shelterforanimals.entity.Record;
import ru.skypro.shelterforanimals.entity.User;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.PetPhotoRepository;
import ru.skypro.shelterforanimals.repository.RecordRepository;
import ru.skypro.shelterforanimals.repository.UserRepository;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.PetPhotoService;
import ru.skypro.shelterforanimals.service.RecordService;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetPhotoServiceImpl implements PetPhotoService {
    private final UserRepository userRepository;

    @Value("${path.to.photos.folder}")
    private String photoDis;
    @Value("${telegram.bot.token}")
    private String value;
    private final PetPhotoRepository petPhotoRepository;
    private final RecordRepository recordRepository;
    private final RecordService recordService;
    private final VolunteerRepository volunteerRepository;
    private final TelegramBot telegramBot;

    /**
     * method creates pet Photo and save in database
     *
     * @param recordId - id record
     * @param photos   - byte[] photos
     * @throws IOException - if stream has problem
     */

    @Override
    public void uploadPhoto(Long recordId, PhotoSize[] photos) throws IOException {
        log.info("Was invoked method for upload avatar");
        Record record = recordService.findById(recordId);
        LocalDate date = LocalDate.now();
        if (record != null ) {
            Long chatId = record.getChatId();
            PetPhoto photoControl = petPhotoRepository.findPetPhotoByChatIdAndDate(chatId, date);
            if (photoControl != null) {
                telegramBot.execute(new SendMessage(chatId, "Вы за этот день уже сохраняли отчет"));
            } else {
                String fileId = photos[0].fileId();
                String fileName = photos[0].fileUniqueId();
                System.out.println("fileId " + fileId);
                System.out.println("fileName " + fileName);

                String botToken = value;
                URL url = new URL("https://api.telegram.org/bot" + botToken + "/" + "getFile?file_id=" + fileId);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String getFileResponse = br.readLine();

                JsonElement jResult = JsonParser.parseString(getFileResponse);
                JsonObject path = jResult.getAsJsonObject().getAsJsonObject("result");
                String file_path = path.get("file_path").getAsString();
                System.out.println("file_path " + file_path);

                Path filePath = Path.of(photoDis, record.getRecordId() + "." +
                        getExtensions(Objects.requireNonNull(fileName)));
                Files.createDirectories(filePath.getParent());
                Files.deleteIfExists(filePath);
                try (
                        InputStream is = new URL("https://api.telegram.org/file/bot" + botToken + "/" + file_path).openConnection().getInputStream();
                        OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                        BufferedInputStream bis = new BufferedInputStream(is, 480);
                        BufferedOutputStream bos = new BufferedOutputStream(os, 480);
                ) {
                    bis.transferTo(bos);
                }
                PetPhoto petPhoto = new PetPhoto();
                petPhoto.setRecord(record);
                petPhoto.setFilePath(filePath.toString());
                petPhoto.setFileSize(photos[0].fileSize());
                petPhoto.setDate(date);
                petPhoto.setChatId(record.getChatId());
                petPhoto.setStatus(record.getStatus());
                br.close();
                petPhotoRepository.save(petPhoto);
                log.info("Photo was saved");
                telegramBot.execute(new SendMessage(petPhoto.getChatId(), "фото Вашего питомца сохранено"));
            }
        }else {
            log.info("Record not found");

        }
    }

    @Override
    public String getExtensions(String fileName) {
        log.debug("Requesting file name:{}", fileName);
        log.info("Was invoked method for get extensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public void photo(Update update) throws IOException {
        Record record = recordRepository.findRecordByChatId(update.message().chat().id());
        long recordId = record.getRecordId();
        PhotoSize[] photos = update.message().photo();
        uploadPhoto(recordId, photos);
    }

    /**
     * method finds and return petPhoto to volunteer by his status
     *
     * @param update - update from the bot
     */
    @Override
    public void getPetPhotoForVolunteer(Update update) {
        log.info("Процесс поиска фото - getPetPhotoForVolunteer");
        long chatId = update.callbackQuery().message().chat().id();
        Volunteer volunteer = volunteerRepository.findByChatId(chatId);
        int status = volunteer.getStatus();
        List<PetPhoto> photos = petPhotoRepository.getPetPhotosByStatus(status);
        if (photos.isEmpty()) {
            telegramBot.execute(new SendMessage(chatId, NOT_FIND_PHOTOS.getMessage()));
            log.info("В базе не найдено фото");
        } else {
            telegramBot.execute(new SendMessage(chatId, FIND_PHOTOS.getMessage() + photos));
            log.info("В базе найдены фото");
        }
    }

    /**
     * once a day, the method checks for the presence<br>
     * of photos of pets (for the current day),<br>
     * which should be sent every day by adoptive parents <br>
     * who are on probation, if there is no photo,<br>
     * the bot sends a reminder to the adopter that you<br>
     * need to send a photo of the pet
     */

    //  @Scheduled(cron = "0 0/2 * * * *") //проверка фото происходит один раз в сутки
    public void checkPhotoAndSendReminder() {
        List<Volunteer> allVolunteers = volunteerRepository.findAll();//все волонтеры
        List<User> allUsers = userRepository.findAll();//все усыновители
        LocalDate dateNow = LocalDate.now();//дата проверки фото
        for (User user : allUsers) {
            long chatIdUser = user.getChatId();//берем чат айди усыновителя
            int status = user.getStatus();//берем статус усыновителя
            List<PetPhoto> photos = petPhotoRepository.getAllByChatId(chatIdUser);//выгружает все фото по данному  chatId и потом нужный ищет по дате
            if (photos != null) {//если фото есть
                for (PetPhoto photo : photos) {
                    LocalDate dateOfRecord = photo.getDate();//ищем дату каждого фото
                    if (!dateOfRecord.equals(dateNow)) {//если с текущей датой фото нет - бот отпавляет напоминание усыновителю
                        System.out.println(dateNow + " dateNow");
                        telegramBot.execute(new SendMessage(chatIdUser, PHOTO.getMessage() + dateNow));
                        log.info("Пользователю направлено напоминание о необходимости прислать фото питомца за конкретный день");
                    }
                }
                for (Volunteer volunteer : allVolunteers) {
                    if (volunteer.getStatus() == status) {
                        telegramBot.execute(new SendMessage(volunteer.getChatId(),
                                "отправил усыновителю напоминание о том, что нужно прилать отчет за  " + dateNow));
                    }
                }
            } else {
                telegramBot.execute(new SendMessage(user.getChatId(),
                        "по данному " + chatIdUser + " в базе нет фото"));
                log.info("по данному " + chatIdUser + " в базе нет фото");
            }

        }
    }


}


