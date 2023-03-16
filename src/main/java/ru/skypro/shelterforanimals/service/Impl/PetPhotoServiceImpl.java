package ru.skypro.shelterforanimals.service.Impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.PetPhoto;
import ru.skypro.shelterforanimals.entity.Record;
import ru.skypro.shelterforanimals.repository.PetPhotoRepository;
import ru.skypro.shelterforanimals.repository.RecordRepository;
import ru.skypro.shelterforanimals.service.PetPhotoService;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetPhotoServiceImpl implements PetPhotoService {

    @Value("${path.to.photos.folder}")
    private String photoDis;
    @Value("${telegram.bot.token}")
    private String value;
    private final PetPhotoRepository petPhotoRepository;
    private final RecordRepository recordRepository;

    @Override
    public void uploadPhoto(Long recordId, PhotoSize[] photos) throws IOException {
        log.info("Was invoked method for upload avatar");
        Record record = recordRepository.findByRecordId(recordId);
        System.out.println(record + " record");
        if (record != null) {
            String fileId = photos[0].fileId();
            String fileName = photos[0].fileUniqueId();
            System.out.println(fileId);
            System.out.println(fileName);

            String botToken = value;
            URL url = new URL("https://api.telegram.org/bot" + botToken + "/" + "getFile?file_id=" + fileId);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String getFileResponse = br.readLine();

            JsonElement jResult = JsonParser.parseString(getFileResponse);
            JsonObject path = jResult.getAsJsonObject().getAsJsonObject("result");
            String file_path = path.get("file_path").getAsString();
            System.out.println(file_path);

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
            br.close();
petPhotoRepository.save(petPhoto);
           // petPhotoRepository.save(petPhoto);
            log.info("Photo was saved");
        } else {
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


}


